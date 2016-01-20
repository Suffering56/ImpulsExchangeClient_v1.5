package impulsexchangeclient;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import org.apache.commons.net.ftp.FTPClient;

public class DataExportLauncher {

    public DataExportLauncher(JProgressBar progressBar, DefaultListModel forExportOrdersModel) {
        this.progressBar = progressBar;
        this.forExportOrdersModel = forExportOrdersModel;
        
        this.progressBar.setString(null);
        this.progressBar.setValue(0);
        createTimer();
    }

    public void runExport() {
        try {
            dataExportThread = new DataExportThread(ftpConnect(), copyModelToList(forExportOrdersModel));
            timer.start();
            dataExportThread.start();
        } catch (IOException ex) {
            String errorMsg = "Неизвестная ошибка.";
            if (ex.toString().contains("UnknownHostException")) {
                errorMsg = "Указан неверный <адрес> FTP-сервера.";
            } else if (ex.toString().contains("FtpLoginException")) {
                errorMsg = "Указан неверный <логин> или <пароль>. \r\n"
                        + "Либо вы указали <адрес> ЧУЖОГО FTP-сервера.";
            } else if (ex.toString().contains("NoRouteToHostException")) {
                errorMsg = "Отсутствует подключение к интернету. Проверьте соединение.";
            }
            JOptionPane.showMessageDialog(null, "Ошибка соединения с FTP-сервером. \r\n"
                    + errorMsg + "\r\n" + "ex.toString(): " + ex.toString(), "DataExportLauncher.runExport()", JOptionPane.ERROR_MESSAGE);
        }
    }

    private FTPClient ftpConnect() throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect(Options.getFtpAddress());
        if (!ftp.login(Options.getFtpLogin(), Options.getFtpPass())) {
            throw new IOException("FtpLoginException");
        }
        ftp.enterLocalPassiveMode();
        return ftp;
    }

    private LinkedList copyModelToList(DefaultListModel dm) {
        LinkedList<String> result = new LinkedList();
        for (int i = 0; i < dm.getSize(); i++) {
            result.add(i, dm.get(i).toString());
        }
        return result;
    }

    private void createTimer() {
        timer = new Timer(10, (ActionEvent e) -> {
            if (dataExportThread.isAlive()) {
                progressBar.setValue(dataExportThread.getProgress());
            } else {
                if (!dataExportThread.isError()) {
                    progressBar.setValue(100);
                    progressBar.setString("Завершено!");
                    forExportOrdersModel.clear();
                } else {
                    progressBar.setValue(100);
                    progressBar.setString("Ошибка!");
                }
                timer.stop();
            }
        });
    }

    private final JProgressBar progressBar;
    private final DefaultListModel forExportOrdersModel;
    private DataExportThread dataExportThread;
    private Timer timer;
}
