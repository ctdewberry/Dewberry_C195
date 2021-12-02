package Main;

import DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/** Main. Starts the Main application
 * <b>
 * C195 Final Project
 * @author Caleb Dewberry
 * WGU Student ID: #001222626
 * </b>
 */

public class Main extends Application {
    private static Main instance;


    /** Sets primary stage
     */


    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.primaryStage = primaryStage;
        // ResourceBundle. Gets resource bundle for localization
        // Resource bundle is sent to the LogIn page as the starting page

        ResourceBundle rb = ResourceBundle.getBundle("Main/rbLang", Locale.getDefault());
//        ResourceBundle rb = ResourceBundle.getBundle("Main/rbLang", Locale.FRENCH);

        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"), rb);
        this.primaryStage.setTitle(rb.getString("Log In"));
        this.primaryStage.setScene(new Scene(root, 600, 400));
        this.primaryStage.show();
    }

    /** The entry point of application.
     *
     * @param args the input arguments
     *
     */
    public static void main(String[] args) {

        // Connects to the database through DBConnection in DAO
        // Closes connection afterwards

        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}

