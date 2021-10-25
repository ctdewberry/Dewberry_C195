package DAO;

import DAO.DBConnection;

import javafx.collections.ObservableList;


import java.sql.*;
import java.util.Arrays;

public class Query {

    public static void testAccess (){
        try {
            String sql = "SELECT * from customers";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

//            Array a = rs.getArray("Customer_Name");
//            String[] nullable = (String[])a.getArray();
//            System.out.println(Arrays.toString(nullable));
            while (rs.next()) {
                System.out.println(rs.getInt("Customer_ID"));
                System.out.println(rs.getString("Customer_Name"));
                System.out.println(rs.getString("Address"));
            }


        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
