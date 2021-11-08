package Model;


public class AppointmentModel {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private int contactID;
    private String contactName;
    private String type;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private int customerID;
    private int userID;



    public AppointmentModel(int appointmentID, String title, String description, String location, String contactName, String type, String startDate, String startTime, String endDate, String endTime, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID; //int
        this.title = title; //string
        this.description = description; //string
        this.location = location; //string
        this.contactID = contactID; //int
        this.contactName = contactName; //string
        this.type = type; //string
        this.startDate = startDate; //string
        this.startTime = startTime; //string
        this.endDate = endDate; //string
        this.endTime = endTime; //string
        this.customerID = customerID; //int
        this.userID = userID; //int

    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

}
