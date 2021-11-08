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
            String sql = "select c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, f.Division, f.Country_ID, n.Country from customers as c join first_level_divisions as f on c.Division_ID = f.Division_ID join countries as n on f.Country_ID = n.Country_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int customerDivisionID = rs.getInt("Division_ID");
                String customerDivision = rs.getString("Division");
                int customerCountryID = rs.getInt("Country_ID");
                String customerCountry = rs.getString("Country");

                CustomerModel C = new CustomerModel(customerID, customerName, customerAddress, customerCode, customerPhone, customerDivisionID, customerDivision, customerCountryID, customerCountry);
                customerList.add(C);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }
}



