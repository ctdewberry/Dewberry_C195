package DAO;

import Controller.LogIn;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.io.*;


/**The class userDAO. SQL queries related to logging in
 */
public class UserDaoImpl {
    /**Test credentials. Run SQL query with username and password combo
     * @param inputUsername the input username
     * @param inputPassword the input password
     */
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

    /**
     * The constant currentUserID.
     */
    public static int currentUserID;
    /**
     * The constant currentUserName.
     */
    public static String currentUserName;

    /** setCredentials. Set user's username for future use
     * @param userName the user name
     */
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

    /**Get current user id. Get userID for future use
     * @return the int
     */
    public static int getCurrentUserID(){
        return currentUserID;
    }

    /** Get current user name. Get username for future use
     * @return the string
     */
    public static String getCurrentUserName(){
        return currentUserName;
    }

    /** Record login. Create or append to file login attempts
     * @param username the username
     * @param success  the success
     * @throws IOException the io exception
     */
    public static void recordLoginAttempts(String username, String success) throws IOException{
        if (username.isEmpty()) {
            username = "No username entered";
        }
        String filename = "login_activity.txt", login;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String loginTime = formatter.format(System.currentTimeMillis());

        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter loginFile = new PrintWriter(fwriter);
        loginFile.println("Attempted login with username: " + username + " on " + loginTime + ". Result: " + success);
        System.out.println("Attempted login with username: " + username + " on " + loginTime + ". Result: " + success);
        loginFile.close();


    }

}
