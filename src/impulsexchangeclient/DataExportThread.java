package impulsexchangeclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamAdapter;

public class DataExportThread extends Thread {

    public DataExportThread(FTPClient ftp, JProgressBar progressBar, List newOrdersList) {
        this.ftp = ftp;
        this.progressBar = progressBar;
        this.newOrdersList = newOrdersList;
        localFile = new File(Options.getLocalFilePath());
        errorStatus = false;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        System.out.println("Start: " + (System.currentTimeMillis() - startTime));
        try {
            changeDirectory();                                  //Проверяем наличие папки "номер_отдела" на FTP сервере
            uploadSwndFile();                                   //Загрузка "swnd5.arc" на сервер
            uploadOrders();                                     //Загрузка информации о заказах на сервер
            updateArchive();                                    //Обновляем архив предыдущих заказов

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
            JOptionPane.showMessageDialog(null, errorMsg + "\r\n" + "Error: " + ex.toString(), "DataExport.run()", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("Stop: " + (System.currentTimeMillis() - startTime));
    }

    private void changeDirectory() throws IOException {
        boolean exist = ftp.changeWorkingDirectory(Options.getDepartmentNumber());
        if (!exist) {
            ftp.makeDirectory(Options.getDepartmentNumber());
            ftp.changeWorkingDirectory(Options.getDepartmentNumber());
        }
    }

    private void uploadSwndFile() throws IOException {
        setProgressListener();
        try (BufferedInputStream localFileStream = new BufferedInputStream(new FileInputStream(localFile))) {
            ftp.storeFile(Options.getSwndFileName(), localFileStream);
        }
    }

    private void uploadOrders() throws IOException {
        try {
            BufferedOutputStream out = new BufferedOutputStream(ftp.appendFileStream("orders.txt"));
            for (String tempList : newOrdersList) {
                String date = new Date(System.currentTimeMillis()).toLocaleString();
                out.write((tempList + "          " + date + "\r\n").getBytes());
            }
            out.close();
        } catch (Exception ex) {
            if (ex.toString().contains("NullPointerException")) {
                errorStatus = true;
                JOptionPane.showMessageDialog(null, "Ошибка соединения с FTP-сервером! Перезапустите программу! " + "Код ошибки: \r\n" + ex.toString(), "DataExport.uploadOrders()", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateArchive() throws IOException {
        String archive = System.getProperty("user.dir") + File.separator + "archive.bin";
        String temp = System.getProperty("user.dir") + File.separator + "temp.bin";
        if (!Files.exists(Paths.get(archive))) {
            Files.createFile(Paths.get(archive));
        } else {
            //переименовываем старый архив, чтобы сохранить данные
            Files.move(Paths.get(archive), Paths.get(temp), StandardCopyOption.REPLACE_EXISTING);
        }
        //создаем новый и добавляем в архив новые заказы
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(archive, true))) {
            for (String tempList : newOrdersList) {
                String date = new Date(System.currentTimeMillis()).toLocaleString();
                out.write((tempList + "          " + date + "\r\n").getBytes());
            }
        }
        //записываем в конец старую информацию
        transferArchive(archive, temp);
    }

    private void transferArchive(String archive, String temp) throws IOException {
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        try {
            try {
                srcChannel = new RandomAccessFile(temp, "r").getChannel();
                dstChannel = new RandomAccessFile(archive, "rw").getChannel();
                dstChannel.transferFrom(srcChannel, dstChannel.size(), srcChannel.size());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ошибка записи архива! " + "Код ошибки: \r\n" + ex.toString(), "DataExport.transferArchive()", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (srcChannel != null) {
                    srcChannel.close();
                }
                if (dstChannel != null) {
                    dstChannel.close();
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "srcChannel.close()/destChanngel.close() " + "Код ошибки: \r\n" + ex.toString(), "DataExport.transferArchive()", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setProgressListener() {
        CopyStreamAdapter streamListener = new CopyStreamAdapter() {
            private int oldPercent = 0;
            private final long length = localFile.length();

            @Override
            public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                int percent = (int) (totalBytesTransferred * 100 / length);
                if ((percent != oldPercent) && (percent < 100)) {
                    oldPercent = percent;
                    progressBar.setValue(percent);
                }
            }
        };
        ftp.setCopyStreamListener(streamListener);
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
