package Model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**Appointment model. Appointment model
 */
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


    /**Instantiates a new Appointment model.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param contactName   the contact name
     * @param type          the type
     * @param startDateTime the start date time
     * @param endDateTime   the end date time
     * @param customerID    the customer id
     * @param userID        the user id
     * @param contactID     the contact id
     */
    public AppointmentModel(int appointmentID, String title, String description, String location, String contactName, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID; //int
        this.title = title; //string
        this.description = description; //string
        this.location = location; //string
        this.contactID = contactID; //int
        this.contactName = contactName; //string
        this.type = type; //string
        this.startDateTime = startDateTime; //LocalDateTime
        this.endDateTime = endDateTime; //LocalDateTime
        this.customerID = customerID; //int
        this.userID = userID; //int

    }

    /**Instantiates a new Appointment model.
     *
     * @param appointmentID the appointment id
     * @param startDateTime the start date time
     * @param endDateTime   the end date time
     */
    public AppointmentModel(int appointmentID, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.appointmentID = appointmentID; //int
        this.startDateTime = startDateTime; //string
        this.endDateTime = endDateTime; //string

    }

    /**Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**Sets appointment id.
     *
     * @param appointmentID the appointment id
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**Gets contact id.
     *
     * @return the contact id
     */
    public int getContactID() {
        return contactID;
    }

    /**Sets contact id.
     *
     * @param contactID the contact id
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**Sets contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }


    /**Gets start date time.
     *
     * @return the start date time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
//        customerNextAppointment = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a z"));

    }

    /**Get start date time string string.
     *
     * @return the string
     */
    public String getStartDateTimeString(){
        return this.getStartDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));

    }


    /**Get start date local date.
     *
     * @return the local date
     */
    public LocalDate getStartDate(){
        return this.startDateTime.toLocalDate();
    }

    /**Gets start time.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {
        return this.startDateTime.toLocalTime();
    }

    /**Get start time string string.
     *
     * @return the string
     */
    public String getStartTimeString(){
        return this.getStartDateTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
    }


    /**Sets start date time.
     *
     * @param startDateTime the start date time
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }


    /**Gets end date time.
     *
     * @return the end date time
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**Get end date time string string.
     *
     * @return the string
     */
    public String getEndDateTimeString(){
        return this.getEndDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));

    }

    /**Get end date local date.
     *
     * @return the local date
     */
//
    public LocalDate getEndDate(){
        return this.endDateTime.toLocalDate();
    }

    /**Gets end time.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {
        return this.endDateTime.toLocalTime();
    }

    /**Get end time string string.
     *
     * @return the string
     */
    public String getEndTimeString(){
        return this.getEndDateTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
    }
    //

    /**Sets end date time.
     *
     * @param endDateTime the end date time
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
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

    /**Gets user id.
     *
     * @return the user id
     */
    public int getUserID() {
        return userID;
    }

    /**Sets user id.
     *
     * @param userID the user id
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

}
