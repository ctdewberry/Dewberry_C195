package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Controller.CustomersScreen;
import Model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerQuery {


    public static ObservableList<CustomerModel> getAllCustomers() {

        ObservableList<CustomerModel> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

//            Array a = rs.getArray("Customer_Name");
//            String[] nullable = (String[])a.getArray();
//            System.out.println(Arrays.toString(nullable));
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



