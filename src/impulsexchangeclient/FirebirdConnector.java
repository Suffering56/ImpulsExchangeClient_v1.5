package impulsexchangeclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class FirebirdConnector {

    private FirebirdConnector() {
        //Приватный конструктор для реализации Singletone 
    }

    public static FirebirdConnector getInstance() {
        if (instance == null) {
            instance = new FirebirdConnector();
        }
        return instance;
    }

    public Connection connect() {
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка при загрузке драйвера (Class.forName). \r\n"
                    + "ex.toString(): " + ex.toString(), "ConnectionFirebird.connect()", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Properties properties = new Properties();
        properties.setProperty("user", "SYSDBA");
        properties.setProperty("password", "masterkey");
        properties.setProperty("charSet", "cp1251");
        String url = "jdbc:firebirdsql:localhost/3050:F:/rab_kopiy.fdb";
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            return connection;
        } catch (SQLException ex) {
            String errorMsg = "Неизвестная ошибка.";
            if (ex.toString().contains("335544344")) {
                errorMsg = "Не удается найти файл базы данных. Проверьте настройки Firebird.";
            } else if (ex.toString().contains("335544721")) {
                errorMsg = "Сервер не найден. Запустите/перезапустите Firebird";
            } else if (ex.toString().contains("335544472")) {
                errorMsg = "Неверный логин и/или пароль. Проверьте настройки Firebird.";
            } else if (ex.toString().contains("335544323")) {
                errorMsg = "Указанный файл не является базой данных Firebird. Проверьте настройки Firebird. \r\n"
                        + "Либо указанный файл базы данных поврежден.";
            }
            JOptionPane.showMessageDialog(null, "Firebird. Ошибка при установлении соединения с базой данных.\r\n"
                    + errorMsg + "\r\n" + "ex.toString(): " + ex.toString(), "ConnectionFirebird.connect()", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private static FirebirdConnector instance = null;
}
