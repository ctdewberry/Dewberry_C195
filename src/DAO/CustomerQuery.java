package DAO;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import Model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerQuery {

    public static String getNextAppointment(int currCust) {

        String customerNextAppointment = null;


        try {
                String sql = "select appointments.Start from appointments WHERE Customer_ID=" +currCust + " ORDER BY Start ASC LIMIT 1";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    customerNextAppointment = rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a z"));

                } else {
                    customerNextAppointment = "No upcoming appointments";
                }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerNextAppointment;
    }


    public static ObservableList<CustomerModel> getAllCustomers() {
        ObservableList<CustomerModel> customerList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                CustomerModel C = new CustomerModel(customerID, customerName, customerAddress, customerCode, customerPhone);
                customerList.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }
}



