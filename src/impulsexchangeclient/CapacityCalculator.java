package impulsexchangeclient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class CapacityCalculator {

    public CapacityCalculator(Statement statement) {
        this.statement = statement;
    }

    public int capacityCalculate(int INVNO, Map<Integer, Integer> ordnoMap) throws SQLException {
        int capacity = 0;
        for (int ORDNO : ordnoMap.keySet()) {
            int QTY = ordnoMap.get(ORDNO);
            int coefficient = getRatio(INVNO, ORDNO);
            capacity += coefficient * QTY;
        }
        return capacity;
    }

    private int getRatio(int INVNO, int ORDNO) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM ORDSPEC where INVNO = " + INVNO
                + " AND DEPNO  = " + Options.getDepartmentName()
                + " AND ORDNO = " + ORDNO);

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

    private final Statement statement;
}
