package Controller;

import DAO.AppointmentQuery;
import DAO.CustomerQuery;
import Model.AppointmentModel;
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

public class AppointmentsScreen implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    /**
     * The Error messages.
     * Array list for gathering error messages when user tries to modify a part
     */


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
//    AppointmentModel, Integer


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
    void onClickAllAppointments(ActionEvent event) throws IOException {
        appointmentsTableView.setItems(DAO.AppointmentQuery.getAllAppointments());
        appointmentsTableView.getSortOrder().add(apptIDCol);

    }

    @FXML
    void onClickCurrentMonth(ActionEvent event) throws IOException {
        appointmentsTableView.setItems(DAO.AppointmentQuery.getMonthlyAppointments());
        appointmentsTableView.getSortOrder().add(apptIDCol);

    }

    @FXML
    void onClickCurrentWeek(ActionEvent event) throws IOException{
        appointmentsTableView.setItems(DAO.AppointmentQuery.getWeeklyAppointments());
        appointmentsTableView.getSortOrder().add(apptIDCol);

    }


    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Page");
        stage.show();
    }


    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Appointments");
        stage.show();
    }


    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {

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
            return;
        }



//        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsModify.fxml"));
//        stage.setScene(new Scene(scene));
//        stage.setTitle("Modify Appointments");
//        stage.show();
    }

    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws IOException {
        try {
            int currentAppointment = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            String currentName = appointmentsTableView.getSelectionModel().getSelectedItem().getDescription();
            Alert alertConfirmAppointmentDelete = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmAppointmentDelete.setTitle("Delete appointment");
            alertConfirmAppointmentDelete.setHeaderText("Delete appointment");
            alertConfirmAppointmentDelete.setContentText("Do you want to delete appointment: " + currentName + "?");
            Optional<ButtonType> result = alertConfirmAppointmentDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                AppointmentQuery.deleteAppointment(currentAppointment);
                appointmentsTableView.setItems(DAO.AppointmentQuery.getAllAppointments());

                Alert alertConfirmAppointmentIsDeleted = new Alert(Alert.AlertType.INFORMATION);
                alertConfirmAppointmentIsDeleted.setTitle("Delete appointment");
                alertConfirmAppointmentIsDeleted.setHeaderText("Delete appointment");
                alertConfirmAppointmentIsDeleted.setContentText("Appointment:" + currentName + " has been deleted.");
                Optional<ButtonType> result2 = alertConfirmAppointmentIsDeleted.showAndWait();
            }
        } catch (Exception e) {
            System.out.println("No selected appointments");
        }
    }


    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentsTableView.setItems(DAO.AppointmentQuery.getAllAppointments());
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptContactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDateTimeCol.setCellValueFactory(
                startDateString -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.set(startDateString.getValue().getStartDateTimeString());
                    return property;
                }
        );
        apptEndDateTimeCol.setCellValueFactory(
                endDateString -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    property.set(endDateString.getValue().getEndDateTimeString());
                    return property;
                }
        );

        apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentsTableView.getSortOrder().add(apptIDCol);

    }
}
