package Main;

import DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;





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
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
        this.primaryStage.setTitle("Log In");
        this.primaryStage.setScene(new Scene(root, 600, 400));
        this.primaryStage.show();
    }

    public static void main(String[] args) {

        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }

//    public void updateTitle(String title) {
//        primaryStage.setTitle(title);
//    }

}

//test test 123 tester 12341234
