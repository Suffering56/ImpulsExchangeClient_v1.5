package impulsexchangeclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.commons.net.ftp.FTPClient;

public class DataExportThread extends Thread {

    public DataExportThread(JProgressBar progressBar, DefaultListModel sentOrdersList) {
        this.progressBar = progressBar;
        this.sentOrdersList = sentOrdersList;
        newOrdersList = cloneList(sentOrdersList);
        localFilePath = new File(Options.getLocalFilePath());   //Полный путь к файлу (включая его название)
        errorStatus = false;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        System.out.println("Start: " + (System.currentTimeMillis() - startTime));
        try {
            directoryExistCheck();                           //Проверяем наличие папки "номер_отдела" на FTP сервере
            uploadFile();                                    //Загрузка "swnd5.arc" на сервер
            uploadDetails();                                 //Загрузка информации о заказах на сервер
            updateArchive();                                 //Обновляем архив предыдущих заказов

//            progressBar.setValue(100);
//            progressBar.setString("Загрузка завершена");
//            sentOrdersList.clear();                          //Очищаем список заказов на MainFrame

        } catch (MalformedURLException ex) {
            errorStatus = true;
            progressBar.setString("Ошибка загрузки");
            JOptionPane.showMessageDialog(null, "Другая ошибка (MalformedURLException): " + ex.toString(), "DataExport.run()", JOptionPane.ERROR_MESSAGE);

        } catch (InterruptedException | IOException ex) {
            String errorMsg;
            if (ex.toString().contains("FileNotFoundException")) {
                errorMsg = "Файл " + Options.getSwndFileName() + " отсутствует, либо указан неверный к нему путь. Проверьте настройки";
            } else if (ex.toString().contains("NoRouteToHostException")) {
                errorMsg = "Ошибка соединения с интернетом.";
            } else if (ex.toString().contains("FtpLoginException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный логин или пароль.";
            } else if (ex.toString().contains("UnknownHostException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный IP-адрес.";
            } else {
                errorMsg = "Другая ошибка.";
            }
            errorStatus = true;
            progressBar.setString("Ошибка загрузки");
            JOptionPane.showMessageDialog(null, errorMsg + " Ex: " + ex.toString(), "DataExport.run()", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Stop: " + (System.currentTimeMillis() - startTime));
    }

    private void directoryExistCheck() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(Options.getFtpAddress());
        ftpClient.login(Options.getFtpLogin(), Options.getFtpPass());
        ftpClient.enterLocalPassiveMode();
        boolean exist = ftpClient.changeWorkingDirectory(Options.getDepartmentNumber());
        if (!exist) {
            ftpClient.makeDirectory(Options.getDepartmentNumber());
            ftpClient.changeWorkingDirectory(Options.getDepartmentNumber());
        }
    }

    private void uploadFile() throws MalformedURLException, IOException, InterruptedException {
        URL ur = new URL("ftp://" + Options.getFtpLogin() + ":" + Options.getFtpPass() + "@" + Options.getFtpAddress()
                + ":/" + Options.getDepartmentNumber() + "/" + Options.getSwndFileName());
        URLConnection urlConnection = ur.openConnection();

        int line, progressValue;
        int i = 0, oldProgressValue = 0;
        double onePercent = localFilePath.length() / 100.0;

        BufferedInputStream localInputStream = new BufferedInputStream(new FileInputStream(localFilePath));
        BufferedOutputStream uploadOutputStream = new BufferedOutputStream(urlConnection.getOutputStream());
        while ((line = localInputStream.read()) != -1) {
            i++;
            progressValue = (int) (i / onePercent);

            uploadOutputStream.write(line);
            if ((progressValue != oldProgressValue) && (progressValue != 100)) {
                Thread.sleep(0);
                progressBar.setValue(progressValue);
            }
            oldProgressValue = progressValue;
        }
        localInputStream.close();
        uploadOutputStream.close();
    }

    private void uploadDetails() throws MalformedURLException, IOException {
        if (!newOrdersList.isEmpty()) {
            URL ur = new URL("ftp://" + Options.getFtpLogin() + ":" + Options.getFtpPass() + "@" + Options.getFtpAddress()
                    + ":/" + Options.getDepartmentNumber() + "/orders.txt");
            URLConnection urlConnection = ur.openConnection();
            LinkedList<String> existingOrdersList = new LinkedList();

            try {                                                               //получаем существующие на FTP-сервере заказы
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                existingOrdersList = getExistingOrders(in);

            } catch (IOException ex) {                                          //пропускаем исключение, в случае отсутствия файла orders.txt на сервере
            } finally {                                                         //записываем новые заказы в orders.txt
                urlConnection = ur.openConnection();
                BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                for (String tempList : existingOrdersList) {
                    out.write((tempList + "\r\n").getBytes());
                }
                for (String tempList : newOrdersList) {
                    String date = new Date(System.currentTimeMillis()).toLocaleString();
                    out.write((tempList + "          " + date + "\r\n").getBytes());
                }
                out.close();
            }
        }
    }

    private void updateArchive() throws IOException {
        File archive = new File(System.getProperty("user.dir") + "\\archive.bin");
        if (!Files.exists(archive.toPath())) {
            Files.createFile(archive.toPath());
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(archive)));
        LinkedList<String> existingArchiveList = getExistingOrders(in);

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(archive));
        for (String tempList : newOrdersList) {
            String date = new Date(System.currentTimeMillis()).toLocaleString();
            out.write((tempList + "          " + date + "\r\n").getBytes());
        }
        for (String tempList : existingArchiveList) {
            out.write((tempList + "\r\n").getBytes());
        }
        out.close();
        newOrdersList.clear();
    }

    private LinkedList<String> getExistingOrders(BufferedReader in) throws IOException {
        String line;
        LinkedList<String> existingOrdersList = new LinkedList();
        while ((line = in.readLine()) != null) {
            existingOrdersList.add(line);
        }
        in.close();
        return existingOrdersList;
    }

    private LinkedList cloneList(DefaultListModel dm) {
        LinkedList<String> result = new LinkedList();
        if (!dm.isEmpty()) {
            for (int i = 0; i < dm.getSize(); i++) {
                result.add(i, dm.get(i).toString());
            }
        }
        return result;
    }

    public boolean isError() {
        return errorStatus;
    }

    private final JProgressBar progressBar;
    private final DefaultListModel sentOrdersList;
    private final File localFilePath;
    private final LinkedList<String> newOrdersList;
    private boolean errorStatus;
}
