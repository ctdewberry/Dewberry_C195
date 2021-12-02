package Model;

/**Customer model. Customer model
 */
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


    /**Instantiates a new Customer model.
     *
     * @param customerID         the customer id
     * @param customerName       the customer name
     * @param customerAddress    the customer address
     * @param customerCode       the customer code
     * @param customerPhone      the customer phone
     * @param customerDivisionID the customer division id
     * @param customerDivision   the customer division
     * @param customerCountryID  the customer country id
     * @param customerCountry    the customer country
     */
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

    /**Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**Sets customer id.
     *
     * @param customerID the customer id
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**Sets customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**Gets customer address.
     *
     * @return the customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**Sets customer address.
     *
     * @param customerAddress the customer address
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**Gets customer code.
     *
     * @return the customer code
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**Sets customer code.
     *
     * @param customerCode the customer code
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**Gets customer phone.
     *
     * @return the customer phone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**Sets customer phone.
     *
     * @param customerPhone the customer phone
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**Gets customer division id.
     *
     * @return the customer division id
     */
    public int getCustomerDivisionID() {
        return customerDivisionID;
    }

    /**Sets customer division id.
     *
     * @param customerDivisionID the customer division id
     */
    public void setCustomerDivisionID(int customerDivisionID) {
        this.customerDivisionID = customerDivisionID;
    }

    /**Gets customer division.
     *
     * @return the customer division
     */
    public String getCustomerDivision() {
        return customerDivision;
    }

    /**Sets customer division.
     *
     * @param customerDivision the customer division
     */
    public void setCustomerDivision(String customerDivision) {
        this.customerDivision = customerDivision;
    }

    /**Gets customer country id.
     *
     * @return the customer country id
     */
    public int getCustomerCountryID() {
        return customerCountryID;
    }

    /**Sets customer country id.
     *
     * @param customerCountryID the customer country id
     */
    public void setCustomerCountryID(int customerCountryID) {
        this.customerCountryID = customerCountryID;
    }

    /**Gets customer country.
     *
     * @return the customer country
     */
    public String getCustomerCountry() {
        return customerCountry;
    }

    /**Sets customer country.
     *
     * @param customerCountry the customer country
     */
    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

}





