

package Controller;

        import DAO.UserDaoImpl;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.text.Font;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.time.ZonedDateTime;
        import java.time.format.DateTimeFormatter;
        import java.util.Optional;
        import java.util.ResourceBundle;
        import java.util.Locale;

/**Log in. Initializes login screen
 */
public class LogIn implements Initializable {

    private static boolean loggedIn;
    /**The Stage.
     */
    Stage stage;
    /**The Scene.
     */
    Parent scene;

    /**Resource bundle. Loads resource bundle
     */
    @FXML
//    private ResourceBundle rbLang = ResourceBundle.getBundle("Main/rbLang", Locale.FRANCE);
    private ResourceBundle rbLang = ResourceBundle.getBundle("Main/rbLang", Locale.getDefault());



    @FXML
    private Label login1;

    @FXML
    private Button login2;

    @FXML
    private Button exitButton;

    @FXML
    private Label dateText;


    /**
     * Log in user.
     */
    public static void logInUser(){
        loggedIn = true;
    }

    /**
     * Log out user.
     */
    public static void logOutUser(){
        loggedIn = false;
    }

    @FXML
    private Label userLocation;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    /**
     * On action exit.
     * Prompts user for confirmation to exit program
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rbLang.getString("Exit program"));
        alert.setHeaderText(rbLang.getString("Exit program"));
        alert.setContentText(rbLang.getString("Do you want to exit?"));
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText(rbLang.getString("OK"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private Label loginSuccess;


    /**
     * On action login.
     * Method to login user
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        /**
         * Sent username and password entered to UserDaoImpl for testing
         */
        UserDaoImpl.testCredentials(username.getText(), password.getText());
        /**
         * Loads Main Page if login successful
         */

        if (loggedIn) {
            /**
             * Sets username for future use
             */
            UserDaoImpl.setCredentials(username.getText());
            /**
             * Send login attempt (successful) to be written to logfile
             */
            UserDaoImpl.recordLoginAttempts(username.getText(), "Log In Attempt Successful");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MainPage.fxml"));
            loader.load();
            MainPage MainPage = loader.getController();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Main Page");
            stage.show();

        } else {

            /**
             * Notifies user of unsuccessful login in native language
             * Misc formatting based on language settings to preserve consistency between languages
             */
            loginSuccess.setText(rbLang.getString("Invalid credentials"));
            loginSuccess.setLayoutX(Integer.valueOf(rbLang.getString("loginUnsuccessX")));
            /**
             * Send login attempt (unsuccessful) to be written to logfile
             */
            UserDaoImpl.recordLoginAttempts(username.getText(), "Log In Attempt Unsuccessful");

            System.out.println("invalid credentials");
        }

    }


    /**
     * Method to return local time zone to provide clarification to user about
     * what time zone they are working in. Used when initializing screen
     * @return
     */
    public static String getUserTimeZone(){
        ZonedDateTime localDateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy z");
        String userLocalDateTime = formatter.format(localDateTime);
        return userLocalDateTime;
    }


    /**
     * Initialize.
     */
    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Formatting of assets on screen to preserve consistent layout between languages
         */
        login1.setFont(new Font("System",Integer.valueOf(rbLang.getString("login1"))));
        login2.setFont(new Font("System", Integer.valueOf(rbLang.getString("login2"))));
        userLocation.setLayoutX(Integer.valueOf(rbLang.getString("dateXCoord")));
        loginSuccess.setLayoutX(Integer.valueOf(rbLang.getString("loginCred")));
        login1.setLayoutX(Integer.valueOf(rbLang.getString("loginXCoord")));
        loginSuccess.setMaxWidth(260);
        dateText.setLayoutX(Integer.valueOf(rbLang.getString("dateTextXCoord")));
        exitButton.setFont(new Font("System", Integer.valueOf(rbLang.getString("exitSize"))));
        if(Boolean.valueOf(rbLang.getString("loginSuccessWrap")) == Boolean.TRUE) {
            loginSuccess.setWrapText(true);
        }
        /**
         * Sets local date and timezone for user clarification
         */
        userLocation.setText(getUserTimeZone());
    }
}
