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
        }

        Properties properties = new Properties();
        properties.setProperty("user", "SYSDBA");
        properties.setProperty("password", "masterkey");
        properties.setProperty("charSet", "cp1251");
        String url = "jdbc:firebirdsql:localhost/3050:E:/rab_kopiy.fdb";
        try {
            Connection connection = DriverManager.getConnection(url, properties);
            return connection;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "ConnectionFirebird.connect()", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private static FirebirdConnector instance = null;
}
