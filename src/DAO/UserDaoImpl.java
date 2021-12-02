package DAO;

import Controller.LogIn;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.io.*;


public class UserDaoImpl {
    public static void testCredentials(String inputUsername, String inputPassword) {
        try {
            String userSearch = "select * from users where User_Name = '" + inputUsername +"' and Password = '" + inputPassword +"'";
            PreparedStatement searchUsername = DBConnection.getConnection().prepareStatement(userSearch);
            ResultSet rs = searchUsername.executeQuery();
            if (rs.next()) {
                LogIn.logInUser();
            } else {
                System.out.println("No matching credentials found");
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

    public static void recordLoginAttempts(String username, String success) throws IOException{
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String loginTime = formatter.format(System.currentTimeMillis());
//        System.out.println(formatter.format(System.currentTimeMillis()));
//        System.out.println(username);
//        System.out.println(success);
        System.out.println("Attempted login with username: " + username + " on " + loginTime + ". Result: " + success);


    }

}
