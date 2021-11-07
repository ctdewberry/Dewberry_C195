

package Controller;

        import DAO.UserDaoImpl;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.net.URL;
        import java.util.Optional;
        import java.util.ResourceBundle;

public class LogIn implements Initializable {

    private static boolean loggedIn;
    Stage stage;
    Parent scene;


//    boolean loggedIn = false;

    public static void logInUser(){
        loggedIn = true;
    }
    public static void logOutUser(){
        loggedIn = false;
    }

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    void onActionExit(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setHeaderText("Exit program");
        alert.setContentText("Do you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private Label loginSuccess;


    @FXML
    void onActionLogin(ActionEvent event) throws IOException {
//        System.out.println(username.getText());
        UserDaoImpl.testCredentials(username.getText(), password.getText());
        if (loggedIn) {
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
            loginSuccess.setText("Invalid credentials");
            System.out.println("invalid credentials");
        }

    }

    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
