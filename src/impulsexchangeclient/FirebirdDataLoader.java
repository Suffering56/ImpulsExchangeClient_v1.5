package impulsexchangeclient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class FirebirdDataLoader {

    public FirebirdDataLoader(String orderName) {
        this.orderName = orderName;
    }

    public void getData() {
        connection = FirebirdConnector.getInstance().connect();
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            System.out.println("CreateStatementException: " + ex);
        }
        //Получам общую информацию о заказе
        int size = getResultSetSize("SELECT count(*) FROM INVOICES where INVN = " + orderName
                + "AND DEPNO  = " + Options.getDepartmentName(), 1);
        if (size > 0) {
            extractGeneralData();
        }
        //Получаем инфмормацию о клиенте
        size = getResultSetSize("SELECT count(*) FROM CLIENTS where CLNUM = " + entity.getClnum()
                + "AND CLDEP  = " + Options.getDepartmentName(), 2);
        if (size > 0) {
            extractClientData();
        }

        //Подсчет количества конструкций
        size = getResultSetSize("SELECT count(*) FROM INVSPEC where INVNO = " + entity.getInvno()
                + "AND DEPNO  = " + Options.getDepartmentName(), 3);
        if (size > 0) {
//            extractConstructionsData();
        }
        System.out.println("entity: " + entity);
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

    private void extractGeneralData() {
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
            while (rs.next()) {

            }
            entity.setConstructionsCount(rs.getInt(""));
            entity.setDelivery(rs.getInt(""));
            entity.setMounting(rs.getInt(""));
            entity.setDismantling(rs.getInt(""));
        } catch (SQLException ex) {
            System.out.println("extractConstructionsData: " + ex);
        }
    }

    private final String orderName;
    private Connection connection;
    private Statement statement;
    private FirebirdOrderEntity entity;
}
