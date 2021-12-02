

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

public class LogIn implements Initializable {

    private static boolean loggedIn;
    Stage stage;
    Parent scene;

    @FXML
    private ResourceBundle rbLang = ResourceBundle.getBundle("Main/rbLang", Locale.getDefault());



    @FXML
    private Label login1;

    @FXML
    private Button login2;

    public static void logInUser(){
        loggedIn = true;
    }
    public static void logOutUser(){
        loggedIn = false;
    }

    @FXML
    private Label userLocation;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

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




    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
        UserDaoImpl.testCredentials(username.getText(), password.getText());

        if (loggedIn) {
            UserDaoImpl.setCredentials(username.getText());
            UserDaoImpl.recordLoginAttempts(username.getText(), "LogIn Attempt Successful");

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
            loginSuccess.setText(rbLang.getString("Invalid credentials"));
            UserDaoImpl.recordLoginAttempts(username.getText(), "LogIn Attempt Unsuccessful");

            System.out.println("invalid credentials");
        }

    }

    private static String getUserTimeZone(){
        ZonedDateTime localDateTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy z");
        String userLocalDateTime = formatter.format(localDateTime);
        return userLocalDateTime;
    }



    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login1.setFont(new Font("System",Integer.valueOf(rbLang.getString("login1"))));
        login2.setFont(new Font("System", Integer.valueOf(rbLang.getString("login2"))));
        loginSuccess.setMaxWidth(260);
        if(Boolean.valueOf(rbLang.getString("loginSuccessWrap")) == Boolean.TRUE) {
            loginSuccess.setWrapText(true);
        }
        userLocation.setText(getUserTimeZone());

    }

}
