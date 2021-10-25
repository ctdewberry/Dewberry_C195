package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    //    private static final String dbName = "NJ86YG@"; //NOTE: Adding the dbName
    private static final String dbName = "client_schedule"; //NOTE: Adding the dbName
    //    private static final String ipAddress = "//wgudb.ucertify.com:3366/";
    private static final String ipAddress = "jdbc:mysql://localhost:3306/" + dbName;

    //This will ultimately build: *jdbc:mysot://wgudb.ucertify.com:3386/WJ96YGO"
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    //private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + "?connectionTinezone=SERVER"; //v8.6.23
    private static final String MYSQLJBCDriver = "com.mysql.jdbc.Driver";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";

    private static Connection conn = null;

    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);//Fine with Driver manager

            System.out.println("Connection successful");
        } catch (SQLException e) {//Use Printstacktrace for outputting exceptions
            e.printStackTrace();
        } catch (ClassNotFoundException e) {//Use Printstacktrace for outputting exceptions
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }
}
