package impulsexchangeclient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import javax.swing.JOptionPane;

public class FirebirdDataLoader {

    public FirebirdDataLoader(String orderName) {
        this.orderName = orderName;
    }

    public FirebirdOrderEntity extractData() {
        connection = FirebirdConnector.getInstance().connect();
        try {
            statement = connection.createStatement();

            if (extractGeneralData()) {             //Получаем информацию о заказе. Затем... если такой существует:
                extractClientData();                //Получаем инфмормацию о клиенте
                extractConstructionsData();         //Подсчет количества конструкций
                extractAdditionalData();            //Получаем информацию о доп. работах
                deleteAfter();                      //!!!Потом удалить вместе с методом
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения данных. \r\n"
                    + "ex.toString(): " + ex, "FirebirdDataLoader.extractData()", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ничего страшного! Ошибка вызвана методом: <connection.close()>. \r\n"
                        + "ex.toString(): " + ex, "FirebirdDataLoader.extractDataА()", JOptionPane.ERROR_MESSAGE);
            }
        }
        return entity;
    }

    private int getResultSetSize(String query, String label) throws SQLException {
        int size = 0;
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            size = rs.getInt("COUNT");
        }
        System.out.println("COUNT[" + label + "] = <" + size + ">");          //!!!Потом удалить
        return size;
    }

    private boolean extractGeneralData() throws SQLException {
        //Проверяем наличие данного заказа в БД
        if (getResultSetSize("SELECT count(*) FROM INVOICES where INVN = " + orderName
                + "AND DEPNO  = " + Options.getDepartmentName(), "GENERAL") > 0) {

            //Получаем основную информацию о заказе
            ResultSet rs = statement.executeQuery("SELECT * FROM INVOICES where INVN = " + orderName
                    + "AND DEPNO  = " + Options.getDepartmentName());
            while (rs.next()) {
                entity = new FirebirdOrderEntity(
                        Options.getDepartmentName() + "/" + orderName,
                        rs.getInt("INVNO"),
                        rs.getInt("CLNUM"));
                entity.setCost(String.format(Locale.US, "%.2f", rs.getDouble("IZDAMOUNT")));
                entity.setMaster(rs.getString("ZMRNAME"));
            }

            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Заказ № <" + Options.getDepartmentName() + "/" + orderName + "> не найден в базе данных СуперОкна!",
                    "FirebirdDataLoader.extractGeneralData()", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    private void extractClientData() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM CLIENTS where CLNUM = " + entity.getClnum()
                + "AND CLDEP  = " + Options.getDepartmentName());
        while (rs.next()) {
            entity.setClient(rs.getString("CLNAME"));
            entity.setAddress(rs.getString("CLADDRESS"));
            entity.setContacts(rs.getString("CLPHONE"));
        }
    }

    private void extractConstructionsData() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM INVSPEC where INVNO = " + entity.getInvno()
                + "AND DEPNO  = " + Options.getDepartmentName());
        int constructionsCount = 0;
        while (rs.next()) {
            constructionsCount += rs.getInt("QTY");
        }
        entity.setConstructionsCount(constructionsCount);
    }

    private void extractAdditionalData() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM DOPWORK where INVNO = " + entity.getInvno()
                + "AND DEPNO  = " + Options.getDepartmentName());
        while (rs.next()) {
            switch (rs.getInt("WRKNO")) {
                case 4:
                    entity.setDismantling("Да");    //демонтаж
                    break;
                case 5:
                    entity.setMounting("Да");       //монтаж
                    break;
                case 6:
                    entity.setDelivery("Да");       //доставка
                    break;
            }
        }
    }

    private final String orderName;
    private Connection connection;
    private Statement statement;
    private FirebirdOrderEntity entity;

    private void deleteAfter() throws SQLException {
        getResultSetSize("SELECT count(*) FROM CLIENTS where CLNUM = " + entity.getClnum()
                + "AND CLDEP  = " + Options.getDepartmentName(), "CLIENTS");
        getResultSetSize("SELECT count(*) FROM INVSPEC where INVNO = " + entity.getInvno()
                + "AND DEPNO  = " + Options.getDepartmentName(), "CONSTR_COUNT");
        getResultSetSize("SELECT count(*) FROM DOPWORK where INVNO = " + entity.getInvno()
                + "AND DEPNO  = " + Options.getDepartmentName(), "DOPWORK");
    }
}
