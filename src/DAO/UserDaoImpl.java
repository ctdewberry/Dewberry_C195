package DAO;

import Controller.LogIn;

import java.sql.*;


public class UserDaoImpl {
    public static void testCredentials(String inputUsername, String inputPassword) {
//        System.out.println(inputUsername);
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
//            System.out.println("invalid credentials");
        }
    }
}
