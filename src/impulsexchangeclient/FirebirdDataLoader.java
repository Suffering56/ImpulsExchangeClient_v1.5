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

    public FirebirdOrderEntity extractData() {
        connection = FirebirdConnector.getInstance().connect();
        if (connection != null) {
            try {
                statement = connection.createStatement();
                if (extractGeneralData()) {             //Получаем информацию о заказе. Затем... если такой существует:
                    extractClientData();                //Получаем инфмормацию о клиенте
                    extractAdditionalData();            //Получаем информацию о доп. работах
                    extractConstructionsData();         //Подсчет количества конструкций
                    capacityCalculate();
//                    deleteAfter();                      //!!!Потом удалить вместе с методом
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
                + " AND DEPNO  = " + Options.getDepartmentName(), "GENERAL") > 0) {

            //Получаем основную информацию о заказе
            ResultSet rs = statement.executeQuery("SELECT * FROM INVOICES where INVN = " + orderName
                    + " AND DEPNO  = " + Options.getDepartmentName());
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

    private void capacityCalculate() throws SQLException {
        int capacity = 0;
        for (int ORDNO : ordnoMap.keySet()) {
            int QTY = ordnoMap.get(ORDNO);
            int coefficient = getCoefficient(ORDNO);
            capacity += coefficient * QTY;
        }
        entity.setCapacity(capacity);
    }

    private int getCoefficient(int ordno) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM ORDSPEC where INVNO = " + entity.getInvno()
                + " AND DEPNO  = " + Options.getDepartmentName()
                + " AND ORDNO = " + ordno);

        int radius = 0;         //[$radius] - арка
        int angle45 = 0;        //[$l] - угол 45 градусов
        int curveAngle = 0;     //[$ln] - кривой угол
        int sash = 0;           //[$pet] - створки
        int fc = 0;             //[$rs] - Рамный соеденитель
        int ic = 0;             //[$si] - Соединитель импоста
        int basic = 0;          //[$base] - База

        //Проверка комплектующих конструкции
        while (rs.next()) {
            // проверка радиуса
            if (rs.getInt("RADIUS") != 0) {
                radius = 1;
            }
            //считаем количество кривых и 45-градусных углов в конструкции
            if ((rs.getInt("ARTNO") == 3) || (rs.getInt("ARTNO") == 1) || (rs.getInt("ARTNO") == 1621) || (rs.getInt("ARTNO") == 1620)
                    || (rs.getInt("ARTNO") == 1285) || (rs.getInt("ARTNO") == 1284) || (rs.getInt("ARTNO") == 197) || (rs.getInt("ARTNO") == 196)) {

                if ((rs.getInt("ALP1") == 45) && (rs.getInt("ALP2") == 45)) {
                    angle45++;
                } else {
                    curveAngle++;
                }
            }
            //считаем количество створок
            if ((rs.getInt("ARTNO") == 1963) || (rs.getInt("ARTNO") == 260) || (rs.getInt("ARTNO") == 295)
                    || (rs.getInt("ARTNO") == 2010) || (rs.getInt("ARTNO") == 175)) {

                sash += rs.getInt("QTY");
            }
            //считаем количество рамных соединителей
            if ((rs.getInt("ARTNO") == 9) || (rs.getInt("ARTNO") == 203)) {
                fc += rs.getInt("QTY");
            }
            //считаем количество соединителей импоста
            if ((rs.getInt("ARTNO") == 159) || (rs.getInt("ARTNO") == 1623) || (rs.getInt("ARTNO") == 201)) {
                ic += rs.getInt("QTY");
            }
        }   //end of while

        if (curveAngle != 0) {
            basic = 6;
            angle45++;
            curveAngle--;
        }
        if (radius != 0) {
            basic = 6;
            angle45--;
            curveAngle++;
        }
        if ((radius == 0) && (curveAngle == 0)) {
            basic = 3;
        }

        int result = basic + (angle45 * 2) + (curveAngle * 4) + (sash * 5) + (fc * 1) + (ic * 2);
        return result;
    }

    private final String orderName;
    private Connection connection;
    private Statement statement;
    private FirebirdOrderEntity entity;
    private final FrameMain mainFrame;
    private final Map<Integer, Integer> ordnoMap = new HashMap<>();
}
