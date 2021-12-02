package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import DAO.AppointmentQuery;
import DAO.UserDaoImpl;
import Model.AppointmentModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainPage implements Initializable {


    Stage stage;

    Parent scene;

    @FXML
    private Text nextApptID;

    @FXML
    private Text nextApptDateTime;

    @FXML
    private Text username;

    @FXML
    private Text nextApptType;

    @FXML
    private Text upcomingAppointments;

    @FXML
    void onActionCustomers(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CustomersScreen.fxml"));
        loader.load();

        CustomersScreen CustomersScreen = loader.getController();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.setTitle("Customers");
        stage.show();


    }



    @FXML
    void onActionBackToLogin(ActionEvent event) throws IOException {
        LogIn.logOutUser();


        ResourceBundle rb = ResourceBundle.getBundle("Main/rbLang", Locale.getDefault());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/LogIn.fxml")), rb);
        stage.setScene(new Scene(scene));
        stage.setTitle(rb.getString("Log In"));
        stage.show();



    }

    @FXML
    void onActionAppointments(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AppointmentsScreen.fxml"));
        loader.load();

        AppointmentsScreen AppointmentsScreen = loader.getController();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.setTitle("Appointments");
        stage.show();

    }


    @FXML
    void onActionReports(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Reports.fxml"));
        loader.load();

        Reports Reports = loader.getController();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.setTitle("Reports");
        stage.show();

    }

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
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(UserDaoImpl.getCurrentUserName());
        if(DAO.AppointmentQuery.checkIfFutureAppointments()) {
            AppointmentModel upcomingAppointment = DAO.AppointmentQuery.getNextAppointment();
            nextApptID.setText(String.valueOf(upcomingAppointment.getAppointmentID()));
            nextApptDateTime.setText(String.valueOf(upcomingAppointment.getStartDateTime().format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a"))));
            nextApptType.setText(String.valueOf(upcomingAppointment.getType()));
            //check if appointment within 15 minutes
            upcomingAppointments.setText(AppointmentQuery.checkIfNextAppointmentIsSoon());
        }
    }


}
