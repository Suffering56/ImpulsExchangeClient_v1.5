package impulsexchangeclient;

public class FirebirdOrderEntity {

    public FirebirdOrderEntity(String fullOrderName, int invno, int clnum) {
        this.fullOrderName = fullOrderName;
        this.invno = invno;
        this.clnum = clnum;
    }

    public void setClient(String client) {
        if (client != null) {
            this.client = client;
        }
    }

    public void setAddress(String address) {
        if (address != null) {
            this.address = address;
        }
    }

    public void setContacts(String contacts) {
        if (contacts != null) {
            this.contacts = contacts;
        }
    }

    public void setCost(String cost) {
        if (cost != null) {
            this.cost = cost;
        }
    }

    public void setMaster(String master) {
        if (master != null) {
            this.master = master;
        }
    }

    public void setConstructionsCount(int constructionsCount) {
        this.constructionsCount = constructionsCount;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public void setMounting(boolean mounting) {
        this.mounting = mounting;
    }

    public void setDismantling(boolean dismantling) {
        this.dismantling = dismantling;
    }

    public String getFullOrderName() {
        return fullOrderName;
    }

    public int getInvno() {
        return invno;
    }

    public int getClnum() {
        return clnum;
    }

    public String getClient() {
        return client;
    }

    public String getAddress() {
        return address;
    }

    public String getContacts() {
        return contacts;
    }

    public String getCost() {
        return cost;
    }

    public String getMaster() {
        return master;
    }

    public int getConstructionsCount() {
        return constructionsCount;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public boolean isMounting() {
        return mounting;
    }

    public boolean isDismantling() {
        return dismantling;
    }

    @Override
    public String toString() {
        return "Entity{" + "fullOrderName=" + fullOrderName + ", invno=" + invno + ", clnum=" + clnum + ", client=" + client + ", address=" + address
                + "\r\n" + ", contacts=" + contacts + ", cost=" + cost + ", master=" + master + ", constructionsCount=" + constructionsCount + ", delivery=" + delivery + ", mounting=" + mounting + ", dismantling=" + dismantling + '}';
    }

    private final String fullOrderName;
    private final int invno;
    private final int clnum;
    private String client = "не указано";
    private String address = "не указано";
    private String contacts = "не указано";
    private String cost = "не указано";
    private String master = "не указано";
    private int constructionsCount = 0;
    private boolean delivery = false;
    private boolean mounting = false;
    private boolean dismantling = false;
}
