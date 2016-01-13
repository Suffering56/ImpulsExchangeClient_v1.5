package impulsexchangeclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.io.CopyStreamAdapter;

public class DataExportThread extends Thread {

    public DataExportThread(FTPClient ftp, JProgressBar progressBar, List newOrdersList) {
        this.ftp = ftp;
        this.progressBar = progressBar;
        this.newOrdersList = newOrdersList;
        localFile = new File(Options.getLocalFilePath());//Полный путь к файлу (включая его название)
        errorStatus = false;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        System.out.println("Start: " + (System.currentTimeMillis() - startTime));
        try {
            directoryExistCheck();                           //Проверяем наличие папки "номер_отдела" на FTP сервере

//            uploadSwndFile();                                //Загрузка "swnd5.arc" на сервер
//            uploadDetails();                                 //Загрузка информации о заказах на сервер
//            updateArchive();                                 //Обновляем архив предыдущих заказов
//            BufferedInputStream in = new BufferedInputStream(ftp.retrieveFileStream("swnd5.arc"));
//            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("F:\\dow\\swnd5.arc"));
//            int line;
//
//            while ((line = in.read()) != -1) {
//                out.write(line);
//            }
//            out.close();
//            in.close();
        } catch (IOException ex) {
            String errorMsg;
            if (ex.toString().contains("FileNotFoundException")) {
                errorMsg = "Файл " + Options.getSwndFileName() + " отсутствует, либо указан неверный к нему путь. Проверьте настройки";
            } else if (ex.toString().contains("NoRouteToHostException")) {
                errorMsg = "Ошибка соединения с интернетом.";
            } else if (ex.toString().contains("FtpLoginException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный логин или пароль.";
            } else if (ex.toString().contains("UnknownHostException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный IP-адрес.";
            } else if (ex.toString().contains("MalformedURLException")) {
                errorMsg = "Ошибка доступа к FTP-серверу. Неверный URL.";
            } else {
                errorMsg = "Неизвестная ошибка!";
            }
            errorStatus = true;
            progressBar.setString("Ошибка загрузки");
            JOptionPane.showMessageDialog(null, errorMsg + "\r\n" + "Error: " + ex.toString(), "DataExport.run()", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Stop: " + (System.currentTimeMillis() - startTime));
    }

    private void directoryExistCheck() throws IOException {
        boolean exist = ftp.changeWorkingDirectory(Options.getDepartmentNumber());
        if (!exist) {
            ftp.makeDirectory(Options.getDepartmentNumber());
            ftp.changeWorkingDirectory(Options.getDepartmentNumber());
        }
    }

    private void uploadSwndFile() throws IOException {
        setListener();
        BufferedInputStream localFileStream = new BufferedInputStream(new FileInputStream(localFile));
        ftp.appendFile(Options.getSwndFileName(), localFileStream);
        localFileStream.close();
    }

    private void setListener() {
        CopyStreamAdapter streamListener = new CopyStreamAdapter() {
            private int oldPercent = 0;
            private final long length = localFile.length();

            @Override
            public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                int percent = (int) (totalBytesTransferred * 100 / length);
                if ((percent != oldPercent) && (percent != 100)) {
                    oldPercent = percent;
                    progressBar.setValue(percent);
                }
//                if (percent > 100) {
//                    System.out.println("percent = " + percent);
//                }
            }
        };

        ftp.setCopyStreamListener(streamListener);
    }

    private void uploadDetails() throws MalformedURLException, IOException {
        if (!newOrdersList.isEmpty()) {
            LinkedList<String> existingOrdersList = new LinkedList();
            if (ftp.listFiles("orders.txt").length > 0) {
                if (ftp.listFiles("orders.txt").length == 1) {
                    long size = ftp.listFiles("orders.txt")[0].getSize();
                } else {
                    JOptionPane.showMessageDialog(null, "Фантастическая ошибка! Несколько одинаковых файлов orders.txt на FTP-сервере!", "DataExport.run()", JOptionPane.ERROR_MESSAGE);
                }
            }
//            URL ur = new URL("ftp://" + Options.getFtpLogin() + ":" + Options.getFtpPass() + "@" + Options.getFtpAddress()
//                    + ":/" + Options.getDepartmentNumber() + "/orders.txt");
//            URLConnection urlConnection = ur.openConnection();

//            try {                                                               //получаем существующие на FTP-сервере заказы
//                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                existingOrdersList = getExistingOrders(in);
//
//            } catch (IOException ex) {                                          //пропускаем исключение, в случае отсутствия файла orders.txt на сервере
//            } finally {                                                         //записываем новые заказы в orders.txt
//                urlConnection = ur.openConnection();
//                BufferedOutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
//                for (String tempList : existingOrdersList) {
//                    out.write((tempList + "\r\n").getBytes());
//                }
//                for (String tempList : newOrdersList) {
//                    String date = new Date(System.currentTimeMillis()).toLocaleString();
//                    out.write((tempList + "          " + date + "\r\n").getBytes());
//                }
//                out.close();
//            }
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

    public boolean isError() {
        return errorStatus;
    }

    private final JProgressBar progressBar;
    private final File localFile;
    private final List<String> newOrdersList;
    private boolean errorStatus;
    private final FTPClient ftp;
}
