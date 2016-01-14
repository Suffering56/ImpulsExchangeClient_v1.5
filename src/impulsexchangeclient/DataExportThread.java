package impulsexchangeclient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamAdapter;

public class DataExportThread extends Thread {

    public DataExportThread(FTPClient ftp, List newOrdersList) {
        this.ftp = ftp;
        this.newOrdersList = newOrdersList;
        localFile = new File(Options.getLocalFilePath());
        errorStatus = false;
    }

    @Override
    public void run() {
        try {
            changeDirectory();                                  //Проверяем наличие папки "номер_отдела" на FTP сервере
            uploadSwndFile();                                   //Загрузка "swnd5.arc" на сервер
            uploadOrders();                                     //Загрузка информации о заказах на сервер
            updateArchive();                                    //Обновляем архив предыдущих заказов '
            ftp.disconnect();
        } catch (IOException ex) {
            String errorMsg;
            if (ex.toString().contains("FileNotFoundException")) {
                errorMsg = "Указан неверный путь к файлу обмена <" + Options.getSwndFileName() + "> \r\n"
                        + "Пожалуйста проверьте настройки. Пункт <Путь к файлу \"" + Options.getSwndFileName() + "\"";
            } else if (ex.toString().contains("CopyStreamException")) {
                errorMsg = "Ошибка отправки данных на FTP-сервер. \r\n"
                        + "Вероятно было прервано соединение с интернетом.";
            } else if (ex.toString().contains("UpdateArchiveException")) {
                errorMsg = "Ошибка записи архива. Недостаточно прав доступа к папке <" + System.getProperty("user.dir") + ">.";
            } else {
                errorMsg = "Неизвестная ошибка!";
            }
            errorStatus = true;
            JOptionPane.showMessageDialog(null, errorMsg + "\r\n" + ex.toString(), "DataExport.run()", JOptionPane.ERROR_MESSAGE);
        }
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
        try (BufferedOutputStream out = new BufferedOutputStream(ftp.appendFileStream("orders.txt"))) {
            for (String tempList : newOrdersList) {
                String date = new Date(System.currentTimeMillis()).toLocaleString();
                out.write((tempList + "          " + date + "\r\n").getBytes());
            }
        }
    }

    private void updateArchive() throws IOException {
        String archive = System.getProperty("user.dir") + File.separator + "archive.bin";
        String temp = System.getProperty("user.dir") + File.separator + "temp.bin";
        try {
            if (!Files.exists(Paths.get(archive))) {
                Files.createFile(Paths.get(archive));
            } else {
                //переименовываем старый архив, чтобы сохранить данные
                Files.move(Paths.get(archive), Paths.get(temp), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new AccessDeniedException("[UpdateArchiveException.createFile]" + "\r\n" + ex.getMessage());
        }

        //создаем новый и добавляем в архив новые заказы
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(archive, true))) {
            for (String tempList : newOrdersList) {
                String date = new Date(System.currentTimeMillis()).toLocaleString();
                out.write((tempList + "          " + date + "\r\n").getBytes());
            }
        }
        //записываем в конец старую информацию
        overwriteFromTemp(archive, temp);
    }

    private void overwriteFromTemp(String archive, String temp) throws IOException {
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;

        try {
            srcChannel = new RandomAccessFile(temp, "r").getChannel();
            dstChannel = new RandomAccessFile(archive, "rw").getChannel();;
            dstChannel.transferFrom(srcChannel, dstChannel.size(), srcChannel.size());
        } catch (IOException ex) {
            throw new AccessDeniedException("[UpdateArchiveException.overwriteFromTemp]" + "\r\n" + ex.getMessage());
        } finally {
            if (srcChannel != null) {
                srcChannel.close();
            }
            if (dstChannel != null) {
                dstChannel.close();
            }
        }
    }

    private void setProgressListener() {
        CopyStreamAdapter streamListener = new CopyStreamAdapter() {
            private int oldProgress = 0;
            private final long length = localFile.length();

            @Override
            public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                progress = (int) (totalBytesTransferred * 100 / length);
                if ((progress != oldProgress) && (progress < 100)) {
                    oldProgress = progress;
                }
            }
        };
        ftp.setCopyStreamListener(streamListener);
    }

    public boolean isError() {
        return errorStatus;
    }

    public int getProgress() {
        return progress;
    }

    private final File localFile;
    private final List<String> newOrdersList;
    private boolean errorStatus;
    private final FTPClient ftp;
    private int progress = 0;
}
