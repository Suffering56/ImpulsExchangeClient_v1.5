package impulsexchangeclient;

public class ImpulsExchangeClient {

    public static void main(String[] args) {
        Options options = new Options();
        options.getOptions();
        MainFrame mainFrame = new MainFrame(options);
        mainFrame.setVisible(true);
    }
}
