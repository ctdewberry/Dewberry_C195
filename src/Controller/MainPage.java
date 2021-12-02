package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
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


/**
 * The type Main page.
 */
public class MainPage implements Initializable {


    /**
     * The Stage.
     */
    Stage stage;

    /**
     * The Scene.
     */
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

    /**
     * On action customers.
     * Method that loads the customer screen where the user can view/add/update/delete customers
     * @param event the event
     * @throws IOException the io exception
     */
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


    /**
     * On action back to login.
     * Method that takes user back to log in screen
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionBackToLogin(ActionEvent event) throws IOException {
        /**
         * Call method to log out user before going back to login page
         */
        LogIn.logOutUser();
//        ResourceBundle rb = ResourceBundle.getBundle("Main/rbLang", Locale.FRENCH);
        ResourceBundle rb = ResourceBundle.getBundle("Main/rbLang", Locale.getDefault());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/view/LogIn.fxml")), rb);
        stage.setScene(new Scene(scene));
        stage.setTitle(rb.getString("Log In"));
        stage.show();
    }

    /**
     * On action appointments.
     * Method that loads the customer screen where the user can view/add/update/delete appointments
     * @param event the event
     * @throws IOException the io exception
     */
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


    /**
     * On action reports.
     * Method that loads the customer screen where the user can view reports
     * @param event the event
     * @throws IOException the io exception
     */
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

    /**
     * On action exit.
     * Exits the program after prompting customer to confirm
     * @param event the event
     * @throws IOException the io exception
     */
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


    /**
     * Initialize.
     */
    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Sets the username for the currently logged in user, viewable on the main page.
         */
        username.setText(UserDaoImpl.getCurrentUserName());
        /**
         * Checks to see if any future appointments at all
         */
        if(DAO.AppointmentQuery.checkIfFutureAppointments()) {
            /**
             * Gets details for future appointments for current user, not necessarily within 15 minutes.
             */
            AppointmentModel upcomingAppointment = DAO.AppointmentQuery.getNextAppointment();
            /**
             * If a future appointment is scheduled, fill out the details on the main page for quick reference for the user.
             */
            nextApptID.setText(String.valueOf(upcomingAppointment.getAppointmentID()));
            nextApptDateTime.setText(String.valueOf(upcomingAppointment.getStartDateTime().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a z"))));
            nextApptType.setText(String.valueOf(upcomingAppointment.getType()));
            //check if appointment within 15 minutes
            /**
             * Checks to see if future appointment is within 15 minutes of login. The method will provide the prompt for the
             * upcoming appointment to the user and return text indicating if there is an upcoming appointment within 15 minutes or not.
             */
            upcomingAppointments.setText(AppointmentQuery.checkIfNextAppointmentIsSoon());
        }
    }


}
