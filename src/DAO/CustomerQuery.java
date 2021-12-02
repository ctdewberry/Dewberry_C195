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



    //query for populating table on customers page
    public static ObservableList<CustomerModel> getAllCustomers() {
        ObservableList<CustomerModel> customerList = FXCollections.observableArrayList();
        try {
            String sql = "select c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, f.Division, f.Country_ID, n.Country from customers as c join first_level_divisions as f on c.Division_ID = f.Division_ID join countries as n on f.Country_ID = n.Country_ID order by Customer_ID";
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


    //query for updating data on customer page when a customer is selected
    public static String getNextAppointment(int currCust) {

        String customerNextAppointment = null;

        try {
            String sql = "select appointments.Start from appointments WHERE Customer_ID=" +currCust + " AND (Start > CURRENT_TIMESTAMP()) ORDER BY Start ASC LIMIT 1";
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


    //query for ensuring the added customer is the lowest ID possible without conflict
    public static Integer getHighestCustomerID(){
        try {
            String sql = "ANALYZE TABLE customers;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Integer newCustomerID = null;
        try {
            String sql = "select auto_increment from information_schema.TABLES where (TABLE_NAME = 'customers');";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                newCustomerID = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return newCustomerID;
    }


    //query for sending customer data to update screen
    public static CustomerModel getCurrentCustomer(Integer currentCustomerID) {
        CustomerModel currentCustomer = null;
        try {
            String sql = "select c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Division_ID, f.Division, f.Country_ID, n.Country from customers as c join first_level_divisions as f on c.Division_ID = f.Division_ID join countries as n on f.Country_ID = n.Country_ID WHERE Customer_ID =" + currentCustomerID + ";";
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

                currentCustomer = new CustomerModel(customerID, customerName, customerAddress, customerCode, customerPhone, customerDivisionID, customerDivision, customerCountryID, customerCountry);
//            return currentCustomer;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return currentCustomer;
    }


    //queries for populating add/update screens
    public static Integer getDivisionIDFromComboBox(String selectedDivision){
        //SELECT first_level_divisions.Division_ID from countries JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID WHERE Division = 'Florida';
        Integer divisionID = null;
        try {
            String sql = "SELECT first_level_divisions.Division_ID from countries JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID WHERE Division ='" + selectedDivision +"'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisionID = rs.getInt("Division_ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionID;
    }

    public static ObservableList<String> getAllCountries(){
        ObservableList<String> countryList = FXCollections.observableArrayList();
        try {
            String sql = "select Country from countries order by Country_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String countryName =  rs.getString("Country");
                countryList.add(countryName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;
    }

    public static ObservableList<String> getFilteredDivisions(String selectedCountry){
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try {
            String sql = "select Division from first_level_divisions INNER JOIN countries on first_level_divisions.Country_ID=countries.Country_ID WHERE countries.Country='" + selectedCountry +"';";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String divisionName =  rs.getString("Division");
                divisionList.add(divisionName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionList;
    }



    //queries for adding/modifying/deleting appointments to/from database
    public static void addCustomer(CustomerModel newCustomer) {

        String newName = newCustomer.getCustomerName();
        String newAddy = newCustomer.getCustomerAddress();
        String newCode = newCustomer.getCustomerCode();
        String newPhone = newCustomer.getCustomerPhone();
        Integer newDiv = newCustomer.getCustomerDivisionID();
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (DEFAULT, '" + newName +"', '"+ newAddy +"', '"+ newCode +"', '"+ newPhone +"', '"+ newDiv +"');";
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate(sql);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void modifyCustomer(CustomerModel updateCustomer) {

        Integer custID = updateCustomer.getCustomerID();
        String newName = updateCustomer.getCustomerName();
        String newAddy = updateCustomer.getCustomerAddress();
        String newCode = updateCustomer.getCustomerCode();
        String newPhone = updateCustomer.getCustomerPhone();
        Integer newDiv = updateCustomer.getCustomerDivisionID();
        try {
            String sql = "UPDATE customers SET Customer_Name = '" + newName +"', Address ='" + newAddy + "', Postal_Code ='" + newCode + "', Phone ='" + newPhone + "', Division_ID ='" + newDiv +"' WHERE Customer_ID = " + custID + ";";
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate(sql);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteCustomer(Integer selectedCustomer) {
        try {
            String sql1 = "DELETE FROM appointments WHERE Customer_ID = '" + selectedCustomer + "'";
            String sql2 = "DELETE FROM customers WHERE Customer_ID = '" + selectedCustomer + "';";
            Statement stmt = DBConnection.getConnection().createStatement();
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}



