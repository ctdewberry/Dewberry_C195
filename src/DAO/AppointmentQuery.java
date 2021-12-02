package DAO;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Controller.AppointmentsAdd;
import Model.AppointmentModel;
import Model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


/**Appointment queries. Various appointment database queries
 */
public class AppointmentQuery {

    /**Get all appointments. SQL queries for populating table on appointments page
     * @return Observable List with all appointments currently scheduled
     */
    public static ObservableList<AppointmentModel> getAllAppointments() {
        ObservableList<AppointmentModel> allAppointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "select a.Appointment_ID, a.Title, a.Description, a.Location, a.Contact_ID, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID FROM appointments as a join contacts as c on a.Contact_ID = c.Contact_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel A = new AppointmentModel(appointmentID, title, description, location, contactName, type, startDateTime, endDateTime, customerID, userID, contactID);
                allAppointmentsList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAppointmentsList;
    }

    /**Get weekly appointments. SQL query for updating appointments filtered by current week
     * @return Observable List of all appointments this week
     */
    public static ObservableList<AppointmentModel> getWeeklyAppointments() {
        ObservableList<AppointmentModel> weeklyAppointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "select a.Appointment_ID, a.Title, a.Description, a.Location, a.Contact_ID, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID FROM appointments as a join contacts as c on a.Contact_ID = c.Contact_ID WHERE YEARWEEK(Start) = YEARWEEK(NOW())";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel A = new AppointmentModel(appointmentID, title, description, location, contactName, type, startDateTime, endDateTime, customerID, userID, contactID);
                weeklyAppointmentsList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return weeklyAppointmentsList;
    }

    /**Get monthly appointments. SQL query for updating appointments filtered by current month
     * @return Observable List with all appointments this month
     */
    public static ObservableList<AppointmentModel> getMonthlyAppointments() {
        ObservableList<AppointmentModel> monthlyAppointmentsList = FXCollections.observableArrayList();
        try {
            String sql = "select a.Appointment_ID, a.Title, a.Description, a.Location, a.Contact_ID, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID FROM appointments as a join contacts as c on a.Contact_ID = c.Contact_ID WHERE(month(Start) = month(NOW()))";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel A = new AppointmentModel(appointmentID, title, description, location, contactName, type, startDateTime, endDateTime, customerID, userID, contactID);
                monthlyAppointmentsList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return monthlyAppointmentsList;
    }


    /**Get highest Appointment Id. SQL query to prevent conflict when adding appointment.
     * @return integer with the next appointment ID on add
     */
    public static Integer getHighestAppointmentID() {
        try {
            String sql = "ANALYZE TABLE appointments;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Integer newAppointmentID = null;
        try {
            String sql = "select auto_increment from information_schema.TABLES where (TABLE_NAME = 'appointments');";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newAppointmentID = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newAppointmentID;
    }


    /**Get current appointment. SQL query for sending selected appointment for update
     * @param currentAppointmentID the current appointment id
     * @return the appointment model being updated
     */
    public static AppointmentModel getCurrentAppointment(Integer currentAppointmentID){
        AppointmentModel currentAppointment = null;
        try {
            String sql = "select * from appointments join contacts where appointments.Contact_ID = contacts.contact_ID and Appointment_ID=" + currentAppointmentID + ";";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                currentAppointment = new AppointmentModel(appointmentID, title, description, location, contactName, type, startDateTime, endDateTime, customerID, userID, contactID);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return currentAppointment;
    }


    /**all appointments for customer. SQL query for validating no schedule conflicts when adding an appointment
     * @param customerID the customer id
     * @return all appointments for selected customer
     */
    public static ArrayList<AppointmentModel> getAllAppointmentsForCustomer(Integer customerID) {
        ArrayList<AppointmentModel> contactAppointmentList = new ArrayList<AppointmentModel>();
        try {
            String sql = "select Appointment_ID, Start, End from appointments where Customer_ID = " + customerID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                AppointmentModel A = new AppointmentModel(appointmentID, startDateTime, endDateTime);
                contactAppointmentList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactAppointmentList;
    }


    /**Get contact id from name. SQL queries for obtaining contact ID of combobox selection
     * @param contactName the contact name
     * @return the contact id associated with the selected name
     */
    public static Integer getContactIDFromName(String contactName) {
        Integer contactID = null;
        try {
            String sql = "select Contact_ID from contacts WHERE Contact_Name = '" + contactName + "' LIMIT 1;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                contactID =  Integer.valueOf(rs.getString("Contact_ID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactID;
    }

    /**Get All Contacts. SQL query to populate contact comboBox
     * @return ObservableList of all contacts
     */
    public static ObservableList<String> getAllContacts() {
        ObservableList<String> allContactsList = FXCollections.observableArrayList();
        try {
            String sql = "select Contact_Name from contacts order by Contact_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String contactName =  rs.getString("Contact_Name");
                allContactsList.add(contactName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContactsList;
    }

    /** Get All Types. SQL query to populate Type comboBox
     * @return ObservableList of all types
     */
    public static ObservableList<String> getAllTypes() {
        ObservableList<String> allTypesList = FXCollections.observableArrayList();
        try {
            String sql = "select DISTINCT Type from appointments;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String typeName =  rs.getString("Type");
                allTypesList.add(typeName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allTypesList;
    }

    /**Get All CustomerIDs. SQL query to populate customerID comboBox
     * @return ObservableList of all customerIDs
     */
    public static ObservableList<String> getAllCustomerIDs() {
        ObservableList<String> allCustomerIDsList = FXCollections.observableArrayList();
        try {
            String sql = "select Customer_ID from customers order by Customer_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String customerID =  rs.getString("Customer_ID");
                allCustomerIDsList.add(customerID);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCustomerIDsList;

    }

    /**Gets all user IDs. SQL query to populate UserID comboBox
     * @return ObservableList of all user IDs
     */
    public static ObservableList<String> getAllUserIDs() {
        ObservableList<String> allUserIDsList = FXCollections.observableArrayList();
        try {
            String sql = "select User_ID from users order by User_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String userID =  rs.getString("User_ID");
                allUserIDsList.add(userID);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUserIDsList;

    }


    /** Add appointment. SQL query to create appointment
     * @param newAppointment newAppointment to be inserted into database
     */
    public static void addAppointment(AppointmentModel newAppointment) {

        int appointmentID;
        String newTitle = newAppointment.getTitle();
        String newDesc = newAppointment.getDescription();
        String newLoc = newAppointment.getLocation();
        int newContactID = newAppointment.getContactID();
        String newContactName = newAppointment.getContactName();
        String newType = newAppointment.getType();


        LocalDateTime newStartDateTime = newAppointment.getStartDateTime();
        LocalDateTime newEndDateTime = newAppointment.getEndDateTime();


        int newCustomerID = newAppointment.getCustomerID();
        int newUserID = newAppointment.getUserID();
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,newTitle);
            ps.setString(2, newDesc);
            ps.setString(3, newLoc);
            ps.setInt(4, newContactID);
            ps.setString(5, newType);
            ps.setTimestamp(6,Timestamp.valueOf(newStartDateTime));
            ps.setTimestamp(7, Timestamp.valueOf(newEndDateTime));
            ps.setInt(8, newCustomerID);
            ps.setInt(9, newUserID);
            int stmt = ps.executeUpdate();




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**Update appointment. SQL query to update appointment
     * @param updateAppointment updateAppointment to be updated to database
     */
    public static void updateAppointment(AppointmentModel updateAppointment) {
        Integer appointmentID = updateAppointment.getAppointmentID();
        String newTitle = updateAppointment.getTitle();
        String newDesc = updateAppointment.getDescription();
        String newLoc = updateAppointment.getLocation();
        int newContactID = updateAppointment.getContactID();
        String newContactName = updateAppointment.getContactName();
        String newType = updateAppointment.getType();


        LocalDateTime newStartDateTime = updateAppointment.getStartDateTime();
        LocalDateTime newEndDateTime = updateAppointment.getEndDateTime();


        int newCustomerID = updateAppointment.getCustomerID();
        int newUserID = updateAppointment.getUserID();
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = " + appointmentID + ";";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1,newTitle);
            ps.setString(2, newDesc);
            ps.setString(3, newLoc);
            ps.setInt(4, newContactID);
            ps.setString(5, newType);
            ps.setTimestamp(6,Timestamp.valueOf(newStartDateTime));
            ps.setTimestamp(7, Timestamp.valueOf(newEndDateTime));
            ps.setInt(8, newCustomerID);
            ps.setInt(9, newUserID);
            int stmt = ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /** Delete appointment. SQL query to delete selected appointment
     * @param selectedAppointment to be deleted from database
     */
    public static void deleteAppointment(Integer selectedAppointment){
        try {
            String sql1 = "DELETE FROM appointments WHERE Appointment_ID = '" + selectedAppointment + "'";
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate(sql1);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /** Get next appointment. SQL query to check for a future appointment
     * @return nextAppointment upcoming appointment for current user
     */
    public static AppointmentModel getNextAppointment() {
        AppointmentModel nextAppointment = null;
        try {
            String sql = "select Appointment_ID, Start, Type, User_ID from appointments WHERE (Start > CURRENT_TIMESTAMP()) AND User_ID = " + UserDaoImpl.getCurrentUserID() + " ORDER By Start ASC LIMIT 1;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = null;
                String description = null;
                String location = null;
                int contactID = 0;
                String contactName = null;
                String type = rs.getString("Type");
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = null;
                int customerID = 0;
                int userID = 0;
                nextAppointment = new AppointmentModel(appointmentID, title, description, location, contactName, type, startDateTime, endDateTime, customerID, userID, contactID);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return nextAppointment;
    }

    /** Check if future appointments. SQL query to determine if future appointment exists
     * @return the boolean
     */
    public static Boolean checkIfFutureAppointments() {
        Boolean futureAppointment = false;
        try {
            String sql = "select Appointment_ID from appointments WHERE (Start > CURRENT_TIMESTAMP()) AND User_ID = " + UserDaoImpl.getCurrentUserID() + " ORDER By Start ASC LIMIT 1;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                futureAppointment = true;

            } else {
                futureAppointment = false;
            }
        } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        return futureAppointment;

    }


    /**appointment within 15. query to see if appointment is within 15 min of login.
     * Alert users if so
     * @return the string
     */
    public static String checkIfNextAppointmentIsSoon() {
        String isApptSoon = null;
        Integer timeDiffStart = 0;
         //SQL query to get data from soonest appointment
        try {
            String sql = "select Appointment_ID, Start, Type, User_ID from appointments WHERE (Start >= CURRENT_TIMESTAMP()) AND User_ID = " + UserDaoImpl.getCurrentUserID() + " ORDER By Start ASC LIMIT 1;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
           // If an appointment is found, get the details to present to the user
            if (rs.next()) {
                ZonedDateTime timeApptStart = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault());
                int appointmentID = rs.getInt("Appointment_ID");
                String startDateTime = timeApptStart.format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a z"));
                //Check to see if the next appointment is within 15 minutes. If so, trigger an alert to the user every time they visit the Main Page
                timeDiffStart = Math.toIntExact(Duration.between(ZonedDateTime.now(), timeApptStart).getSeconds() / 60);
                if (timeDiffStart <= 15) {
                    isApptSoon = "Your next appointment \nis within 15 minutes";
                    Alert alertAppointmentSoon = new Alert(Alert.AlertType.INFORMATION);
                    alertAppointmentSoon.setTitle("Upcoming Appointment");
                    alertAppointmentSoon.setHeaderText("Your next appointment is within 15 minutes");
                    alertAppointmentSoon.setContentText("Appointment ID: " + appointmentID + "\n" + "Appointment starts at: " + startDateTime);
                    alertAppointmentSoon.showAndWait();
                } else {
                    isApptSoon = "No appointments within \n15 minutes for "+ UserDaoImpl.getCurrentUserName();
                }
            } else {
                isApptSoon = "No appointments within \n15 minutes for user: "+ UserDaoImpl.getCurrentUserName();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isApptSoon;
    }

}
