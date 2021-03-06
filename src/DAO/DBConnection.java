package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**Initialize database connected. Prepare to connect to DB
 */
public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String dbName = "client_schedule"; //NOTE: Adding the dbName
    private static final String ipAddress = "//localhost:3306/";
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    private static final String MYSQLJBCDriver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";

    private static Connection conn = null;

    /**Start connection. Attempt database connection
     * @return the connection
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);//Fine with Driver manager

            System.out.println("Connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**Get connection. Allow access to connection once established
     * @return the connection
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * Close connection.
     */
    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }
}
