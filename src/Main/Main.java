package Main;

import DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {
    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        this.primaryStage = primaryStage;
        ResourceBundle rb = ResourceBundle.getBundle("Main/rbLang", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"), rb);
        this.primaryStage.setTitle(rb.getString("Log In"));
        this.primaryStage.setScene(new Scene(root, 600, 400));
        this.primaryStage.show();
    }

    public static void main(String[] args) {

        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }



}

