package DAO;

import DAO.DBConnection;

import Model.AppointmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Reports query.
 */
public class ReportsQuery {

    /**
     * Report month choices observable list.
     * Populates month comboBox
     * @return the observable list
     */
//queries to populate report option prefills
    public static ObservableList<String> ReportMonthChoices(){
        ObservableList<String> ReportMonthOptions = FXCollections.observableArrayList();
        try {
            String sql = "select DISTINCT DATE_FORMAT (Start, '%m-%Y') from appointments;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportMonthOptions.add(rs.getString("DATE_FORMAT (Start, '%m-%Y')"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ReportMonthOptions;
    }

    /**
     * Report type choices observable list.
     * Populates type comboBox
     * @return the observable list
     */
    public static ObservableList<String> ReportTypeChoices(){
        ObservableList<String> ReportTypeOptions = FXCollections.observableArrayList();
        try {
            String sql = "select Distinct Type from appointments;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportTypeOptions.add(rs.getString("Type"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ReportTypeOptions;
    }

    /**
     * Report contact choices observable list.
     * Populates contact comboBox
     * @return the observable list
     */
    public static ObservableList<String> ReportContactChoices(){
        ObservableList<String> ReportContactOptions = FXCollections.observableArrayList();
        try {
            String sql = "select distinct Contact_Name from contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportContactOptions.add(rs.getString("Contact_Name"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ReportContactOptions;
    }

    /**
     * Report location choices observable list.
     * Populates location comboBox
     * @return the observable list
     */
    public static ObservableList<String> ReportLocationChoices(){
        ObservableList<String> ReportContactOptions = FXCollections.observableArrayList();
        try {
            String sql = "select distinct Location from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ReportContactOptions.add(rs.getString("Location"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ReportContactOptions;
    }

    /**
     * Report A totals query integer.
     * SQL query to obtain Report A results
     * @param reportMonth the report month
     * @param reportYear  the report year
     * @param reportType  the report type
     * @return the integer
     */
//queries to run reports
    public static Integer ReportATotalsQuery(Integer reportMonth, Integer reportYear, String reportType){
        int ReportATotals = 0;
        try {
            String sql = "select count(*) from appointments where Type = '" + reportType + "' and year(Start) = " + reportYear + " and month(Start) = " +reportMonth;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReportATotals = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ReportATotals;
    }

    /**
     * Report B appointments by contact observable list.
     * SQL query to obtain Report B results
     * @param reportContactName the report contact name
     * @return the observable list
     */
    public static ObservableList<AppointmentModel> ReportBAppointmentsByContact(String reportContactName) {
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
                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel contactAppointments = new AppointmentModel(appointmentID, title, description, location, null, type, startDateTime, endDateTime, customerID, userID, contactID);
                contactAppointmentList.add(contactAppointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return contactAppointmentList;
    }

    /**
     * Report C appointments by location observable list.
     * SQL query to obtain Report C results
     * @param reportByLocation the report by location
     * @return the observable list
     */
    public static ObservableList<AppointmentModel> ReportCAppointmentsByLocation(String reportByLocation) {
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

                LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                AppointmentModel contactAppointments = new AppointmentModel(appointmentID, title, description, location, null, type, startDateTime, endDateTime, customerID, userID, contactID);
                reportAppointmentList.add(contactAppointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return reportAppointmentList;
    }
}
