package DAO;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import Model.AppointmentModel;
import Model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


public class AppointmentQuery {

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
            String sql = "select Type from appointments;";
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
                String startDate = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String startTime = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                String endDate = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String endTime = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel A = new AppointmentModel(appointmentID, title, description, location, contactName, type, startDate, startTime, endDate, endTime, customerID, userID, contactID);
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
                String startDate = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String startTime = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                String endDate = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String endTime = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel A = new AppointmentModel(appointmentID, title, description, location, contactName, type, startDate, startTime, endDate, endTime, customerID, userID, contactID);
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
                String startDate = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String startTime = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                String endDate = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String endTime = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel A = new AppointmentModel(appointmentID, title, description, location, contactName, type, startDate, startTime, endDate, endTime, customerID, userID, contactID);
                monthlyAppointmentsList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return monthlyAppointmentsList;
    }

    public static ObservableList<AppointmentModel> getAppointmentsByContact(String reportContactName) {
        ObservableList<AppointmentModel> contactAppointmentList = FXCollections.observableArrayList();
        try {
            String sql = "select a.Appointment_ID, a.Title, a.Description, a.Location, a.Contact_ID, c.Contact_Name, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID FROM appointments as a join contacts as c on a.Contact_ID = c.Contact_ID WHERE Contact_Name = '"+reportContactName +"'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String contactName = null;
                String type = rs.getString("Type");
                String startDate = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String startTime = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                String endDate = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String endTime = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel contactAppointments = new AppointmentModel(appointmentID, title, description, location, null, type, startDate, startTime, endDate, endTime, customerID, userID, contactID);
                contactAppointmentList.add(contactAppointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contactAppointmentList;
    }

    public static ObservableList<AppointmentModel> getAppointmentsByLocation(String reportByLocation) {
        ObservableList<AppointmentModel> reportAppointmentList = FXCollections.observableArrayList();
        try {
            String sql = "select * from appointments WHERE Location = '" + reportByLocation + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String contactName = null;
                String type = rs.getString("Type");
                String startDate = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String startTime = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                String endDate = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String endTime = rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel contactAppointments = new AppointmentModel(appointmentID, title, description, location, null, type, startDate, startTime, endDate, endTime, customerID, userID, contactID);
                reportAppointmentList.add(contactAppointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return reportAppointmentList;
    }

    public static ObservableList<AppointmentModel> getNextAppointment() {
        ObservableList<AppointmentModel> nextAppointmentList = FXCollections.observableArrayList();
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
                String startDate = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                String startTime = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("hh:mm a"));
                String endDate = null;
                String endTime = null;
                int customerID = 0;
                int userID = 0;
                AppointmentModel A = new AppointmentModel(appointmentID, title, description, location, contactName, type, startDate, startTime, endDate, endTime, customerID, userID, contactID);
                nextAppointmentList.add(A);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return nextAppointmentList;
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

    public static String checkNextAppointmentTime() {
        String isApptSoon = null;
        Integer timeDiff = 0;
        try {
            String sql = "select Appointment_ID, Start, Type, User_ID from appointments WHERE (Start > CURRENT_TIMESTAMP()) AND User_ID = " + UserDaoImpl.getCurrentUserID() + " ORDER By Start ASC LIMIT 1;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ZonedDateTime timeAppt = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault());
                timeDiff = Math.toIntExact(Duration.between(ZonedDateTime.now(), timeAppt).getSeconds() / 60);
                if (timeDiff <= 15) {
                    isApptSoon = "Your next appointment is within 15 minutes";
                    Alert alertAppointmentSoon = new Alert(Alert.AlertType.INFORMATION);
                    alertAppointmentSoon.setTitle("Upcoming Appointment");
                    alertAppointmentSoon.setHeaderText("Your next appointment is within 15 minutes");
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

//    private int appointmentID;
//    private String type;
//    private String startDate;
//    private String startTime;
//    private String endTime;
//    private int customerID;
//    private int userID;
//    private int contactID;