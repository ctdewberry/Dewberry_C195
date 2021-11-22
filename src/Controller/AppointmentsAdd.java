package Controller;

import DAO.AppointmentQuery;
import DAO.CustomerQuery;
import Model.AppointmentModel;
import Model.CustomerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;

import static DAO.AppointmentQuery.getContactIDFromName;

public class AppointmentsAdd implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Text newApptID;

    @FXML
    private TextField apptTitle;

    @FXML
    private TextField apptDesc;

    @FXML
    private TextField apptLoc;

    @FXML
    private ComboBox comboBoxApptContact = new ComboBox();

    @FXML
    private ComboBox comboBoxApptType = new ComboBox();

    @FXML
    private DatePicker datePickerApptStartDate;

    @FXML
    private TextField apptStartTime;

    @FXML
    private DatePicker datePickerApptEndDate;

    @FXML
    private TextField apptEndTime;

    @FXML
    private ComboBox comboBoxApptCustID = new ComboBox();

    @FXML
    private ComboBox comboBoxApptUserID = new ComboBox();




    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        SimpleDateFormat apptDateFormat = new SimpleDateFormat();
//        SimpleTimeZone apptTimeFormat = new SimpleTimeZone("HH:mm a");
        LocalTime localTimeStart = LocalTime.parse(apptStartTime.getText(), DateTimeFormatter.ofPattern("hh:mm:ss a"));
        LocalTime localTimeEnd = LocalTime.parse(apptEndTime.getText(), DateTimeFormatter.ofPattern("hh:mm:ss a"));
        LocalDate localDateStart = LocalDate.parse(datePickerApptStartDate.getValue().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate localDateEnd = LocalDate.parse(datePickerApptEndDate.getValue().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        int id = Integer.parseInt(newApptID.getText());
        String title = apptTitle.getText();
        String desc = apptDesc.getText();
        String loc = apptLoc.getText();
        //create method to obtain integer from value of combobo
        int contactID = getContactIDFromName(comboBoxApptContact.getSelectionModel().getSelectedItem().toString());
        String contactName = comboBoxApptContact.getSelectionModel().getSelectedItem().toString();
//                Integer.valueOf((String) comboBoxApptContact.getSelectionModel().getSelectedItem());

        String type = comboBoxApptType.getSelectionModel().getSelectedItem().toString();
//        LocalDate startDate = datePickerApptStartDate.getValue();

        String startTime = null;
        String startDate = null;
        String endTime = null;
        String endDate = null;

        startTime = localTimeStart.toString();
            System.out.println(localTimeStart);
            System.out.println(startTime);

        endTime = localTimeEnd.toString();
        System.out.println(localTimeEnd);
        System.out.println(endTime);

        startDate = localDateStart.toString();
        System.out.println(localDateStart);
        System.out.println(startDate);

        endDate = localDateEnd.toString();
        System.out.println(localDateEnd);
        System.out.println(endDate);



//        LocalDateTime startTime = new DateTime(formatter.parse(apptStartTime.getText()).getTime());
//        LocalDate endDate = datePickerApptEndDate.getValue();
//        Time endTime = new java.sql.Time(formatter.parse(apptEndTime.getText()).getTime());;
        int customerID = Integer.parseInt(comboBoxApptCustID.getSelectionModel().getSelectedItem().toString());
        int userID = Integer.parseInt(comboBoxApptUserID.getSelectionModel().getSelectedItem().toString());

        Alert alertConfirmAppointmentCreation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmAppointmentCreation.setTitle("Add new appointment");
        alertConfirmAppointmentCreation.setHeaderText("Add new appointment");
        alertConfirmAppointmentCreation.setContentText("Do you want to create an appointment of type: " + type + "?");
        Optional<ButtonType> result = alertConfirmAppointmentCreation.showAndWait();
        if (result.get() == ButtonType.OK) {
            AppointmentQuery.addAppointment(new AppointmentModel(id, title, desc, loc, contactName, type, startDate, startTime, endDate, endTime, customerID, userID, contactID));

            Alert alertConfirmAppointmentIsAdded = new Alert(Alert.AlertType.INFORMATION);
            alertConfirmAppointmentIsAdded.setTitle("Add appointment");
            alertConfirmAppointmentIsAdded.setHeaderText("Add appointment");
            alertConfirmAppointmentIsAdded.setContentText("Appointment for " + type + " has been scheduled.");
            Optional<ButtonType> result2 = alertConfirmAppointmentIsAdded.showAndWait();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/AppointmentsScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Customers");
            stage.show();
        }

    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        Alert alertConfirmCancel = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmCancel.setTitle("Cancel");
        alertConfirmCancel.setHeaderText("Cancel");
        alertConfirmCancel.setContentText("Do you want to Cancel?");

        Optional<ButtonType> result = alertConfirmCancel.showAndWait();
        if (result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/AppointmentsScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Appointments");
            stage.show();
        }
    }



    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxApptContact.getItems().setAll(AppointmentQuery.getAllContacts());
        comboBoxApptType.getItems().setAll(AppointmentQuery.getAllTypes());
        comboBoxApptCustID.getItems().setAll(AppointmentQuery.getAllCustomerIDs());
        comboBoxApptUserID.getItems().setAll(AppointmentQuery.getAllUserIDs());
        newApptID.setText(String.valueOf(AppointmentQuery.getHighestAppointmentID()));

    }
}
