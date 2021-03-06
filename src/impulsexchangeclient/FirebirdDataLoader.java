package impulsexchangeclient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;

public class FirebirdDataLoader {

    public FirebirdDataLoader(FrameMain mainFrame, String orderName) {
        this.mainFrame = mainFrame;
        this.orderName = orderName;
    }

    public OrderEntity extractData() {
        connection = FirebirdConnector.getInstance().connect();
        if (connection != null) {
            try {
                statement = connection.createStatement();
                if (extractGeneralData()) {             //Получаем информацию о заказе. Затем... если такой существует:
                    extractClientData();                //Получаем инфмормацию о клиенте
                    extractAdditionalData();            //Получаем информацию о доп. работах
                    extractConstructionsData();         //Подсчет количества конструкций
                    exctractCapacity();
                } else {
                    new FrameNewOrder(mainFrame).setVisible(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "НЕИЗВЕСТНАЯ ошибка чтения данных. \r\n"
                        + "ex.toString(): " + ex, "FirebirdDataLoader.extractData()", JOptionPane.ERROR_MESSAGE);
                mainFrame.setEnabled(true); //если возникла НЕИЗВЕСТНАЯ ошибка
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
        } else {    //если возникла ошибка при установлении соединения с БД (FirebirdConnector.connect())
            mainFrame.setEnabled(true);
        }
        return entity;
    }

    private boolean extractGeneralData() throws SQLException {
        //Проверяем наличие данного заказа в БД
        if (getResultSetSize("SELECT count(*) FROM INVOICES where INVN = " + orderName
                + " AND DEPNO  = " + Options.getDepartmentName(), "GENERAL") > 0) {

            //Получаем основную информацию о заказе
            ResultSet rs = statement.executeQuery("SELECT * FROM INVOICES where INVN = " + orderName
                    + " AND DEPNO  = " + Options.getDepartmentName());
            while (rs.next()) {
                entity = new OrderEntity(
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
                + " AND CLDEP  = " + Options.getDepartmentName());
        while (rs.next()) {
            entity.setClient(rs.getString("CLNAME"));
            entity.setAddress(rs.getString("CLADDRESS"));
            entity.setContacts(rs.getString("CLPHONE"));
        }
    }

    private void extractAdditionalData() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM DOPWORK where INVNO = " + entity.getInvno()
                + " AND DEPNO  = " + Options.getDepartmentName());
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

    private void extractConstructionsData() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM INVSPEC where INVNO = " + entity.getInvno()
                + " AND DEPNO  = " + Options.getDepartmentName());
        int constructionsCount = 0;
        ordnoMap.clear();
        while (rs.next()) {
            int QTY = rs.getInt("QTY");
            constructionsCount += QTY;
            ordnoMap.put(rs.getInt("ORDNO"), QTY);  //key = ORDNO, value = QTY
        }
        entity.setConstructionsCount(constructionsCount);
    }

    private void exctractCapacity() throws SQLException {
        CapacityCalculator capacityCalc = new CapacityCalculator(statement);
        int capacity = capacityCalc.capacityCalculate(entity.getInvno(), ordnoMap);
        entity.setCapacity(capacity);
    }

    private int getResultSetSize(String query, String label) throws SQLException {
        int size = 0;
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            size = rs.getInt("COUNT");
        }
        return size;
    }

    private final String orderName;
    private Connection connection;
    private Statement statement;
    private OrderEntity entity;
    private final FrameMain mainFrame;
    private final Map<Integer, Integer> ordnoMap = new HashMap<>();
}
