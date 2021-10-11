package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LogIn.fxml"));
        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}

//test test 123 tester 12341234
