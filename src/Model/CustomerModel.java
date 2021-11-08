package Model;

public class CustomerModel {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerCode;
    private String customerPhone;
    private int customerDivisionID;
    private String customerDivision;
    private int customerCountryID;
    private String customerCountry;



    public CustomerModel(int customerID, String customerName, String customerAddress, String customerCode, String customerPhone, int customerDivisionID, String customerDivision, int customerCountryID, String customerCountry) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerCode = customerCode;
        this.customerPhone = customerPhone;
        this.customerDivisionID = customerDivisionID;
        this.customerDivision = customerDivision;
        this.customerCountryID=customerCountryID;
        this.customerCountry = customerCountry;
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

    public int getCustomerDivisionID() {
        return customerDivisionID;
    }

    public void setCustomerDivisionID(int customerDivisionID) {
        this.customerDivisionID = customerDivisionID;
    }

    public String getCustomerDivision() {
        return customerDivision;
    }

    public void setCustomerDivision(String customerDivision) {
        this.customerDivision = customerDivision;
    }

    public int getCustomerCountryID() {
        return customerCountryID;
    }

    public void setCustomerCountryID(int customerCountryID) {
        this.customerCountryID = customerCountryID;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

}





