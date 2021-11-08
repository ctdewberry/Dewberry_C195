package DAO;

import Controller.LogIn;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class UserDaoImpl {
    public static void testCredentials(String inputUsername, String inputPassword) {
        try {
            String userSearch = "select * from users where User_Name = '" + inputUsername +"' and Password = '" + inputPassword +"'";
            PreparedStatement searchUsername = DBConnection.getConnection().prepareStatement(userSearch);
            ResultSet rs = searchUsername.executeQuery();
            if (rs.next()) {
                System.out.println("found it");
                LogIn.logInUser();
            } else {
                System.out.println("not found");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static int currentUserID;
    public static String currentUserName;
    public static void setCredentials(String userName) {
        try {
            String sql = "select User_ID, User_Name from users where User_Name = '" + userName +"'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                currentUserID = rs.getInt("User_ID");
                currentUserName = rs.getString("User_Name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public static int getCurrentUserID(){
        return currentUserID;
    }
    public static String getCurrentUserName(){
        return currentUserName;
    }
}
