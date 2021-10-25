package Model;

public class CustomerModel {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerCode;
    private String customerPhone;

    public CustomerModel(int customerID, String customerName, String customerAddress, String customerCode, String customerPhone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerCode = customerCode;
        this.customerPhone = customerPhone;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}



//    public CustomerModel(int customerID, String customerName, String customerAddress, String customerCode, String customerPhone) {
//        this.id = id;
//        this.customerName = customerName;
//        this.address = address;
//        this.postalCode = postalCode;
//        this.phone = phone;
//    }



