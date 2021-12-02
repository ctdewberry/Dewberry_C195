package Controller;

import DAO.AppointmentQuery;
import DAO.CustomerQuery;
import Model.AppointmentModel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** The Appointments screen. This is where the Appointments screen is initialized
 * */
public class AppointmentsScreen implements Initializable {

    /**The Stage.
     */
    Stage stage;
    /**The Scene.
     */
    Parent scene;



    @FXML
    private RadioButton allAppointmentsButton;

    @FXML
    private ToggleGroup appointmentTimeFrame;

    @FXML
    private TableView<AppointmentModel> appointmentsTableView;

    @FXML
    private RadioButton currentMonthBtn;

    @FXML
    private RadioButton currentWeekBtn;

    @FXML
    private TableColumn<AppointmentModel, Integer> apptIDCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptTitleCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptDescCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptLocCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptContactNameCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptTypeCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptStartDateTimeCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptEndDateTimeCol;

    @FXML
    private TableColumn<AppointmentModel, Integer> apptCustIDCol;

    @FXML
    private TableColumn<AppointmentModel, Integer> apptUserIDCol;

    @FXML
    private TableColumn<AppointmentModel, Integer> apptContactIDCol;

    @FXML
    private Label userZone;


    /**
     * onClickAllAppointments. Set the table view to view all appointments.
     * sorted by appointment id
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onClickAllAppointments(ActionEvent event) throws IOException {
        appointmentsTableView.setItems(DAO.AppointmentQuery.getAllAppointments());
        appointmentsTableView.getSortOrder().add(apptIDCol);

    }

    /**
     * On click current month.
     * Sets the table view to view all appointments in current month, sorted by appointment id
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onClickCurrentMonth(ActionEvent event) throws IOException {
        appointmentsTableView.setItems(DAO.AppointmentQuery.getMonthlyAppointments());
        appointmentsTableView.getSortOrder().add(apptIDCol);

    }

    /**
     * On click current week.
     * Sets the table view to view all appointments in current week, sorted by appointment id
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onClickCurrentWeek(ActionEvent event) throws IOException{
        appointmentsTableView.setItems(DAO.AppointmentQuery.getWeeklyAppointments());
        appointmentsTableView.getSortOrder().add(apptIDCol);

    }


    /**
     * On action back.
     * Returns to main page
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Page");
        stage.show();
    }


    /**
     * On action add appointment.
     * Opens window to add a new appointment
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Appointments");
        stage.show();
    }


    /**
     * On action modify appointment.
     * Opens window to modify selected appointment
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        /**
         * Attempts to get selected appointment to send to the modify appointment screen
         */
        try {
            int currentAppointment = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AppointmentsModify.fxml"));
            loader.load();
            AppointmentsModify AppointmentModify = loader.getController();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            AppointmentModify.sentAppointment(AppointmentQuery.getCurrentAppointment(currentAppointment));
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Appointment");
            stage.show();
        } catch (NullPointerException e) {
            /*
              Catches exception if no user selected when attempting to modify
             */
            return;
        }



    }

    /**
     * On action delete appointment.
     * Deletes the selected appointment
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws IOException {
        /*
          Attempts to delete selected appointment
         */
        try {
            int currentAppointment = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            String currentType = appointmentsTableView.getSelectionModel().getSelectedItem().getType();
            Alert alertConfirmAppointmentDelete = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmAppointmentDelete.setTitle("Delete appointment");
            alertConfirmAppointmentDelete.setHeaderText("Delete appointment");
            alertConfirmAppointmentDelete.setContentText("Do you want to delete appointment " + currentAppointment + " for: " + currentType + "?");
            Optional<ButtonType> result = alertConfirmAppointmentDelete.showAndWait();
            /*
              Confirmation window on delete
             */
            if (result.get() == ButtonType.OK) {
                AppointmentQuery.deleteAppointment(currentAppointment);
                appointmentsTableView.setItems(DAO.AppointmentQuery.getAllAppointments());
                Alert alertConfirmAppointmentIsDeleted = new Alert(Alert.AlertType.INFORMATION);
                alertConfirmAppointmentIsDeleted.setTitle("Delete appointment");
                alertConfirmAppointmentIsDeleted.setHeaderText("Delete appointment");
                alertConfirmAppointmentIsDeleted.setContentText("Appointment " + currentAppointment + " for: " + currentType + " has been deleted.");
                Optional<ButtonType> result2 = alertConfirmAppointmentIsDeleted.showAndWait();
                appointmentsTableView.getSortOrder().add(apptIDCol);
            }
            /**
             * Catches exception if user presses delete and no appointment is selected
             */
        } catch (Exception e) {
            Alert noAppointmentsSelectedForDeletion = new Alert(Alert.AlertType.INFORMATION);
            noAppointmentsSelectedForDeletion.setTitle("Delete appointment");
            noAppointmentsSelectedForDeletion.setHeaderText("Delete appointment");
            noAppointmentsSelectedForDeletion.setContentText("Please select an appointment for deletion.");
            Optional<ButtonType> result2 = noAppointmentsSelectedForDeletion.showAndWait();
        }
    }


    /**
     * Initialize.
     */
    @FXML
    void initialize() {

    }

    /**
     * initialize. initializes table and values on screen load
     * Lambda used below to format and localize the time and date of the incoming appointments DateTime values
     * I used this because it was the only way I could find to properly format and localize the dateTime for use in the
     * table column. I use this lambda several time in this project but it is not my only use of a lambda
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
           Initializes table view and various labels and buttons on appointment screen
         */

        userZone.setText(LogIn.getUserTimeZone());
        appointmentsTableView.setItems(DAO.AppointmentQuery.getAllAppointments());
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptContactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        /**
         * lambda methods to format the start dates of appointments listed on table in an easily readable format
         * while also localizing the date and time to the current timezone
         */
        apptStartDateTimeCol.setCellValueFactory( startDateString -> new ReadOnlyStringWrapper(startDateString.getValue().getStartDateTimeString()));

        /**
         * lambda methods to format the end dates of appointments listed on table in an easily readable format
         * while also localizing the date and time to the current timezone
         */
        apptEndDateTimeCol.setCellValueFactory( endDateString -> new ReadOnlyStringWrapper(endDateString.getValue().getEndDateTimeString()));


        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentsTableView.getSortOrder().add(apptIDCol);

    }
}
