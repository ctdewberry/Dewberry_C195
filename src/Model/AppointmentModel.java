package Model;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class AppointmentModel {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private int contactID;
    private String contactName;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerID;
    private int userID;



    public AppointmentModel(int appointmentID, String title, String description, String location, String contactName, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID; //int
        this.title = title; //string
        this.description = description; //string
        this.location = location; //string
        this.contactID = contactID; //int
        this.contactName = contactName; //string
        this.type = type; //string
        this.startDateTime = startDateTime; //string
        this.endDateTime = endDateTime; //string
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
//        customerNextAppointment = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a z"));

    }
    public String getStartDateTimeString(){
        return this.getStartDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));

    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }


    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getEndDateTimeString(){
        return this.getEndDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));

    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
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
