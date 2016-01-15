package impulsexchangeclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Options {

    public static void setOptions() {
        String departmentNumberWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeClient /v departmentNumber /t REG_SZ /d " + departmentNumber + " /f";
        String localFilePathWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeClient /v localFilePath /t REG_SZ /d " + localFilePath + " /f";
        String swndFileNameWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeClient /v swndFileName /t REG_SZ /d " + swndFileName + " /f";
        String ftpAddressWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeClient /v ftpAddress /t REG_SZ /d " + ftpAddress + " /f";
        String ftpLoginWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeClient /v ftpLogin /t REG_SZ /d " + ftpLogin + " /f";
        String ftpPassWQuery = "REG ADD HKCU\\Software\\ImpulsExchangeClient /v ftpPass /t REG_SZ /d " + ftpPass + " /f";

        String optionsWriteQuery[] = {departmentNumberWQuery, localFilePathWQuery, swndFileNameWQuery, ftpAddressWQuery,
            ftpLoginWQuery, ftpPassWQuery};                                     //Инициализация запросов на изменение реестра

        for (String query : optionsWriteQuery) {
            Process process;
            try {
                process = Runtime.getRuntime().exec(query);                     
                while (process.isAlive()) {}                                    //ждем пока процесс выполнит свою работу
                process.destroy();                                              //уничтожаем процесс
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ошибка записи реестра.\r\n"
                        + "ex.toString(): " + ex.toString(), "Options.setOptions()", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void getOptions() {                                                  //Чтение настроек реестра
        try {
            String dataOptionsQuery[] = {depNum, filePath, fileName,
                address, login, password};                                      //Инициализация запросов к реестру
            LinkedList<String> optionsList = new LinkedList();
            int nullOptionsCounter = 0;

            for (String query : dataOptionsQuery) {
                Process process = Runtime.getRuntime().exec(query);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));       //Чтение ключей/значений реестра
                String line;
                String parseLine = "";

                while ((line = reader.readLine()) != null) {
                    if (line.contains("REG_SZ")) {                              //Извлечение нужной строки потока реестра
                        parseLine = line.trim();                                //PS: там почему-то не в одной строке все хранится
                    }
                }
                reader.close();
                process.destroy();

                Matcher m = p.matcher(parseLine);
                if (m.matches()) {
                    optionsList.add(m.group(1));                               //Извлечение нужного значения ключа реестра...
                } else {                                                       //... либо если ключ(значение) отсутствует/не соответствует шаблону...
                    optionsList.add("");                                       //... извлечение пустого значения (для избежания ошибки)             
                    nullOptionsCounter++;
                }
            }

            if (nullOptionsCounter == 6) {
                firstStart();                                                  //Загрузка значений по-умолчанию при первом запуске программы
                setOptions();
            } else {
                importOptionsIntoProgramm(optionsList);                        //Импорт извлеченных параметров в класс Options
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения реестра.\r\n"
                    + "ex.toString(): " + ex.toString(), "Options.getOptions()", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void importOptionsIntoProgramm(LinkedList<String> optionsList) {
        departmentNumber = optionsList.get(0);
        localFilePath = optionsList.get(1);
        swndFileName = optionsList.get(2);
        ftpAddress = optionsList.get(3);
        ftpLogin = optionsList.get(4);
        ftpPass = optionsList.get(5);
    }

    private static void firstStart() {
        departmentNumber = "100";
        localFilePath = "c:\\swnd5.arc";
        swndFileName = "swnd5.arc";
        ftpAddress = "5.101.156.8";
        ftpLogin = "mailru5o_login";
        ftpPass = "im699000pass";
    }

    public static String getFtpLogin() {
        return ftpLogin;
    }

    public static void setFtpLogin(String ftpLogin) {
        Options.ftpLogin = ftpLogin;
    }

    public static String getFtpPass() {
        return ftpPass;
    }

    public static void setFtpPass(String ftpPass) {
        Options.ftpPass = ftpPass;
    }

    public static String getFtpAddress() {
        return ftpAddress;
    }

    public static void setFtpAddress(String ftpAddress) {
        Options.ftpAddress = ftpAddress;
    }

    public static String getLocalFilePath() {
        return localFilePath;
    }

    public static void setLocalFilePath(String localFilePath) {
        Options.localFilePath = localFilePath;
    }

    public static String getDepartmentNumber() {
        return departmentNumber;
    }

    public static void setDepartmentNumber(String departmentNumber) {
        Options.departmentNumber = departmentNumber;
    }

    public static String getSwndFileName() {
        return swndFileName;
    }

    public static void setSwndFileName(String swndFileName) {
        Options.swndFileName = swndFileName;
    }

    private static String departmentNumber;
    private static String localFilePath;
    private static String swndFileName;
    private static String ftpLogin;
    private static String ftpPass;
    private static String ftpAddress;

    private static final Pattern p = Pattern.compile(
            "\\w+\\p{Space}+REG_SZ\\p{Space}+(.+)");          //Шаблон для извлечения значений реестра

    private static final String depNum = "REG QUERY HKCU\\Software\\ImpulsExchangeClient /v departmentNumber";
    private static final String filePath = "REG QUERY HKCU\\Software\\ImpulsExchangeClient /v localFilePath";
    private static final String fileName = "REG QUERY HKCU\\Software\\ImpulsExchangeClient /v swndFileName";
    private static final String address = "REG QUERY HKCU\\Software\\ImpulsExchangeClient /v ftpAddress";
    private static final String login = "REG QUERY HKCU\\Software\\ImpulsExchangeClient /v ftpLogin";
    private static final String password = "REG QUERY HKCU\\Software\\ImpulsExchangeClient /v ftpPass";
}
