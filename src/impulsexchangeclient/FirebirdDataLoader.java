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

    public FirebirdOrderEntity getData() {
        connection = FirebirdConnector.getInstance().connect();
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println("CreateStatementException: " + ex);
        }

        if (extractGeneralData()) {
            //=====================Получаем инфмормацию о клиенте//=====================
            getResultSetSize("SELECT count(*) FROM CLIENTS where CLNUM = " + entity.getClnum()
                    + "AND CLDEP  = " + Options.getDepartmentName(), 2);
            extractClientData();

            //=====================Подсчет количества конструкций//=====================
            getResultSetSize("SELECT count(*) FROM INVSPEC where INVNO = " + entity.getInvno()
                    + "AND DEPNO  = " + Options.getDepartmentName(), 3);
            extractConstructionsData();

            //=====================Получаем информацию о доп. работах//=====================
            getResultSetSize("SELECT count(*) FROM DOPWORK where INVNO = " + entity.getInvno()
                    + "AND DEPNO  = " + Options.getDepartmentName(), 4);
            extractAdditionalData();
        }

        //=====================Закрываем connection//=====================
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("CloseConnectionError: " + ex);
        }
        System.out.println("entity: " + entity);
        return entity;
    }

    private int getResultSetSize(String query, int counter) {
        int size = 0;
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                size = rs.getInt("COUNT");
            }
            System.out.println("COUNT[" + counter + "] = <" + size + ">");
        } catch (SQLException ex) {
            System.out.println("getResultSetSize: " + ex);
        }
        return size;
    }

    private boolean extractGeneralData() {
        //Если: в базе найдены записи о данном заказе
        if (getResultSetSize("SELECT count(*) FROM INVOICES where INVN = " + orderName
                + "AND DEPNO  = " + Options.getDepartmentName(), 1) > 0) {
            //Получаем основную информацию
            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM INVOICES where INVN = " + orderName
                        + "AND DEPNO  = " + Options.getDepartmentName());
                while (rs.next()) {
                    entity = new FirebirdOrderEntity(
                            orderName + "/" + Options.getDepartmentName(),
                            rs.getInt("INVNO"),
                            rs.getInt("CLNUM"));
                    entity.setCost(String.format(Locale.US, "%.2f", rs.getDouble("IZDAMOUNT")));
                    entity.setMaster(rs.getString("ZMRNAME"));
                }
            } catch (SQLException ex) {
                System.out.println("extractGeneralData: " + ex);
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Отсутствует информация о заказе <" + orderName + "> в базе данных СуперОкна!",
                    "FirebirdDataLoader.extractGeneralData()", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
    }

    private void extractClientData() {
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM CLIENTS where CLNUM = " + entity.getClnum()
                    + "AND CLDEP  = " + Options.getDepartmentName());
            while (rs.next()) {
                entity.setClient(rs.getString("CLNAME"));
                entity.setAddress(rs.getString("CLADDRESS"));
                entity.setContacts(rs.getString("CLPHONE"));
            }
        } catch (SQLException ex) {
            System.out.println("extractClientData: " + ex);
        }
    }

    private void extractConstructionsData() {
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM INVSPEC where INVNO = " + entity.getInvno()
                    + "AND DEPNO  = " + Options.getDepartmentName());
            int constructionsCount = 0;
            while (rs.next()) {
                constructionsCount += rs.getInt("QTY");
            }
            entity.setConstructionsCount(constructionsCount);
        } catch (SQLException ex) {
            System.out.println("extractConstructionsData: " + ex);
        }
    }

    private void extractAdditionalData() {
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM DOPWORK where INVNO = " + entity.getInvno()
                    + "AND DEPNO  = " + Options.getDepartmentName());
            while (rs.next()) {
                switch (rs.getInt("WRKNO")) {
                    case 4:
                        entity.setDismantling(true);
                        break;
                    case 5:
                        entity.setMounting(true);
                        break;
                    case 6:
                        entity.setDelivery(true);
                        break;
                }

            }
        } catch (SQLException ex) {
            System.out.println("extractAdditionalData: " + ex);
        }
    }

    private final String orderName;
    private Connection connection;
    private Statement statement;
    private FirebirdOrderEntity entity;
}
