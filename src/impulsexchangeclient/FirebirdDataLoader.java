package impulsexchangeclient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
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
                    deleteAfter();                      //!!!Потом удалить вместе с методом
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
        int capacity = 0;
        while (rs.next()) {
            constructionsCount += rs.getInt("QTY");

            //????????????????????????????/
            int factor = getFactor(rs.getString("ORDNO"));
            capacity = capacity + factor * rs.getInt("QTY");
        }
        entity.setConstructionsCount(constructionsCount);
    }

    private int getFactor(String ordno) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM ORDSPEC where INVNO = " + entity.getInvno()
                + " AND DEPNO  = " + Options.getDepartmentName()
                + " AND ORDNO = " + ordno);
        while (rs.next()) {
            
        }
        return 0;
//        function nagruz($invno,$ordno,$dn,$database) {
//
//          $database = "localhost:F:/rab_kopiy.FDB";
//          $user = "SYSDBA";
//          $password = "masterkey";
//          $l = 0;  //   угол 45 градусов
//          $ln = 0;  // кривой угол
//          $radius = 0; // арка
//          $pet = 0; // створки
//          $rs = 0;  // Рамный соеденитель
//          $si = 0; // Соединитель импоста
//          $base = 0; //  База
//          $db = ibase_connect($database, $user, $password);

//            $result = ibase_query("SELECT * FROM ORDSPEC WHERE INVNO = ".$invno." AND DEPNO  = ".$dn." AND ORDNO = ".$ordno); 	!
//		while ($row = ibase_fetch_object($result)) {
        
//         // проверка комплектующих конструкции
//                if ($row -> radius != 0) {
//                    $radius = 1;
//                } // проверка радиуса
//                If(($row -> ARTNO == 3) or($row -> ARTNO == 1) or($row -> ARTNO == 1621) or($row -> ARTNO == 1620) or($row -> ARTNO == 1285) or($row -> ARTNO == 1284) or($row -> ARTNO == 197) or($row -> ARTNO == 196)
//                
//                    ) { 
//				
//					if (($row -> ALP1 != {
//                        
//                    }
//                    '45') or($row -> ALP2 !=
//                    
//                        '45')) { $ln++;
//                    }else {$l++;} //если			
//                }
//                if (($row -> ARTNO == {
//                    
//                }
//                '1963') or($row -> ARTNO ==
//                '260') or($row -> ARTNO ==
//                '295') or($row -> ARTNO ==
//                '2010') or($row -> ARTNO ==
//                
//                    '175')){
//				$pet = $pet + (int) $row -> QTY;
//                }
//                if (($row -> ARTNO == {
//                    
//                }
//                '159') or($row -> ARTNO ==
//                '1623') or($row -> ARTNO ==
//                
//                    '201')){
//				$si = $si + (int) $row -> QTY;
//                }
//                if (($row -> ARTNO == '9')  {
//                    or($row -> ARTNO ==
//                }
//                
//                    '203')){
//				$rs = $rs + (int) $row -> QTY;
//                }
//
//            }// конец проверки комплектующих конструкции	
//
//            if ($ln != 0) {
//                $base = 6;
//                $l = $l + 1;
//                $ln = $ln - 1;
//            }
//            if ($radius != 0) {
//                $base = 6;
//                $l = $l - 1;
//                $ln = $ln + 1;
//            }
//            if (($radius == 0)  {
//                and($ln == 0)
//            }
//            
//                ) {$base = 3;
//            }
//            $res = ($l * 2) + $base + ($ln * 4) + ($pet * 5) + ($rs * 1) + (($si / 2) * 4);	//	Формула echo "<br> LN-".$ln."L-".$l."База-".$base."RS-".$rs."SI-".$si."PET-".$pet." ";
//            return $res;
//        }
    }

    private final String orderName;
    private Connection connection;
    private Statement statement;
    private FirebirdOrderEntity entity;
    private final FrameMain mainFrame;

    private void deleteAfter() throws SQLException {
        getResultSetSize("SELECT count(*) FROM CLIENTS where CLNUM = " + entity.getClnum()
                + "AND CLDEP  = " + Options.getDepartmentName(), "CLIENTS");
        getResultSetSize("SELECT count(*) FROM INVSPEC where INVNO = " + entity.getInvno()
                + "AND DEPNO  = " + Options.getDepartmentName(), "CONSTR_COUNT");
        getResultSetSize("SELECT count(*) FROM DOPWORK where INVNO = " + entity.getInvno()
                + "AND DEPNO  = " + Options.getDepartmentName(), "DOPWORK");
    }
}
