package DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import Model.AppointmentModel;
import Model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AppointmentQuery {

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
                String startDate =  rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
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

    public static ObservableList<AppointmentModel> getWeeklyAppointments(){
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
                String startDate =  rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
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

    public static ObservableList<AppointmentModel> getMonthlyAppointments(){
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
                String startDate =  rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
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

    public static ObservableList<AppointmentModel> getNextAppointment(){
        ObservableList<AppointmentModel> nextAppointmentList = FXCollections.observableArrayList();
        try {
            String sql = "select Appointment_ID, Start, Type from appointments WHERE Start > CURRENT_TIMESTAMP() ORDER By Start ASC LIMIT 1;";
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
                String startDate =  rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy"));
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
    public static String checkNextAppointmentTime(){
    String isApptSoon = null;
        try {
            String sql = "select Appointment_ID, Start, Type from appointments WHERE Start > CURRENT_TIMESTAMP() ORDER By Start ASC LIMIT 1;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ZonedDateTime timeAppt = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault());
                isApptSoon = timeAppt.toString();
//                timeDiff = timeAppt - ZonedDateTime.now();
//                System.out.println(timeAppt - ZonedDateTime.now());
//            rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a z"));
            } else {
                isApptSoon = "negative";
                System.out.println("yo");
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