package impulsexchangeclient;

public class FirebirdOrderEntity {

    public FirebirdOrderEntity(String fullOrderName, int invno, int clnum) {
        this.fullOrderName = fullOrderName;
        this.invno = invno;
        this.clnum = clnum;
    }

    public void setClient(String client) {
        if ((client != null) && (!client.trim().isEmpty())) {
            this.client = client;
        }
    }

    public void setAddress(String address) {
        if ((address != null) && (!client.trim().isEmpty())) {
            this.address = address;
        }
    }

    public void setContacts(String contacts) {
        if ((contacts != null) && (!client.trim().isEmpty())) {
            this.contacts = contacts;
        }
    }

    public void setCost(String cost) {
        if ((cost != null) && (!client.trim().isEmpty())) {
            this.cost = cost;
        }
    }

    public void setMaster(String master) {
        if ((master != null) && (!client.trim().isEmpty())) {
            this.master = master;
        }
    }

    public void setConstructionsCount(int constructionsCount) {
        this.constructionsCount = constructionsCount;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public void setMounting(String mounting) {
        this.mounting = mounting;
    }

    public void setDismantling(String dismantling) {
        this.dismantling = dismantling;
    }

    //=====================доп поля начало==========================
    public void setMountingDate(String mountingDate) {
        this.mountingDate = mountingDate;
    }

    public void setLamination(boolean lamination) {
        this.lamination = lamination;
    }

    public void setGarbage(boolean garbage) {
        this.garbage = garbage;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //=====================доп поля конец==========================

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

    public String getDelivery() {
        return delivery;
    }

    public String getMounting() {
        return mounting;
    }

    public String getDismantling() {
        return dismantling;
    }

    //=====================доп поля начало==========================
    public String getMountingDate() {
        return mountingDate;
    }

    public boolean isLamination() {
        return lamination;
    }

    public boolean isGarbage() {
        return garbage;
    }

    public String getComment() {
        return comment;
    }

    public String getDescription() {
        return description;
    }
    //=====================доп поля конец==========================

    @Override
    public String toString() {
        return "Entity{" + "fullOrderName=" + fullOrderName + ", invno=" + invno + ", clnum=" + clnum + ", client=" + client + ", address=" + address
                + "\r\n" + ", contacts=" + contacts + ", cost=" + cost + ", master=" + master + ", constructionsCount=" + constructionsCount + ", delivery=" + delivery + ", mounting=" + mounting + ", dismantling=" + dismantling + '}';
    }

    //обязательные поля
    private final String fullOrderName;
    private final int invno;
    private final int clnum;
    //изменяемые поля
    private String client = "не указано";
    private String address = "не указано";
    private String contacts = "не указано";
    //неизменяемые поля
    private int constructionsCount = 0;
    private String delivery = "Нет";
    private String mounting = "Нет";
    private String dismantling = "Нет";
    private String cost = "не указано";         //не нужно  - не отображать - можно вообще удалить
    private String master = "не указано";       //возможно тоже можно удалить
    
    //данные, вводимые дилерами
    private String mountingDate = "не указано";
    private boolean lamination = false;
    private boolean garbage = false;
    private String comment = "";
    private String description = "";
}
