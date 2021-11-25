package DAO;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import Controller.AppointmentsAdd;
import Model.AppointmentModel;
import Model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


public class AppointmentQuery {

    //queries for adding/modifying/deleting appointments
    public static void addAppointment(AppointmentModel newAppointment) {
        //run db insert command to add customer
        //INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (DEFAULT, 'test2', 'testAddy', 'testCode', 'Phone', '104');
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
//            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (DEFAULT, '" + newTitle + "', '" + newDesc + "', '" + newLoc + "', " + newContactID + ", '" + newType + "', ?, ?, " + newCustomerID + ", " + newUserID + ");";
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


//            '" + Timestamp.valueOf(newStartDateTime) + "', '" + Timestamp.valueOf(newEndDateTime) + "'
//            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES (DEFAULT, '" + newTitle + "', '" + newDesc + "', '" + newLoc + "', '" + newContactID + "', '" + newType + "', '" + newStartDate + " " + newStartTime + "', '" + newEndDate + " " + newEndTime + "', '" + newCustomerID + "', '" + newUserID + "');";



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Integer getHighestAppointmentID() {
        Integer newAppointmentID = null;
        try {
            String sql = "select MAX(Appointment_ID) from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newAppointmentID = rs.getInt("MAX(Appointment_ID)")+1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return newAppointmentID;
    }


    //queries for populating appointment add/update screens
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


    //queries for populating table on appointments page
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
//                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));

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
//                String startDateTime = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));
//                String endDateTime = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));
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
//                String startDateTime = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));
//                String endDateTime = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));
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


    //queries to populate main page
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
//                String startDateTime = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));
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


    //query to see if appointment is within 15 min of login
    public static String checkIfNextAppointmentIsSoon() {
        String isApptSoon = null;
        Integer timeDiffStart = 0;
        try {
            String sql = "select Appointment_ID, Start, Type, User_ID from appointments WHERE (Start >= CURRENT_TIMESTAMP()) AND User_ID = " + UserDaoImpl.getCurrentUserID() + " ORDER By Start ASC LIMIT 1;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ZonedDateTime timeApptStart = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault());

                int appointmentID = rs.getInt("Appointment_ID");
                String startDateTime = rs.getTimestamp("Start").toLocalDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"));
                ////???
                timeDiffStart = Math.toIntExact(Duration.between(ZonedDateTime.now(), timeApptStart).getSeconds() / 60);
                if (timeDiffStart <= 15) {
                    isApptSoon = "Your next appointment is within 15 minutes";
                    Alert alertAppointmentSoon = new Alert(Alert.AlertType.INFORMATION);
                    alertAppointmentSoon.setTitle("Upcoming Appointment");
                    alertAppointmentSoon.setHeaderText("Your next appointment is within 15 minutes");
                    alertAppointmentSoon.setContentText("Appointment ID: " + appointmentID + "\n" + "Appointment starts at: " + startDateTime);
                    alertAppointmentSoon.showAndWait();
                } else {
                    isApptSoon = "No upcoming appointments";
                }

            } else {
                isApptSoon = "No upcoming appointments";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isApptSoon;
    }

}
