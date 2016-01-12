package impulsexchangeclient;

import java.io.IOException;
import java.sql.SQLException;

public class ImpulsExchangeClient {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Options options = new Options();
        options.getOptions();
        MainFrame mainFrame = new MainFrame(options);
        mainFrame.setVisible(true);
    }
}
