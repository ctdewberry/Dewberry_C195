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


    public static String timeConversion(TextField timeInput) {
        String timeOutput = null;
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("h:mm a");
        DateTimeFormatter convertFormat = DateTimeFormatter.ofPattern("H:mm:ss");
        LocalTime parsedInput = LocalTime.parse(timeInput.getText(), parseFormat);
        String convertedInput = convertFormat.format(parsedInput);
        timeOutput = convertedInput;
//        System.out.println(timeOutput);
        return timeOutput;
    }

//    public static String dateConversion(DatePicker dateInput) {
    public static String dateConversion(DatePicker dateInput) {

        String dateOutput = null;
        try {
            LocalDate myInputDate = dateInput.getValue();
            dateOutput = myInputDate.toString();
            dateInput.setValue(null);
            dateInput.getEditor().setText(dateOutput);
        } catch (Exception e) {
            System.out.println(e);
            String myTextStart = dateInput.getEditor().getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/[uuuu][uu]");
            LocalDate date = LocalDate.parse(myTextStart, formatter);
            dateOutput = date.toString();
        }
        return dateOutput;
    }


    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
//        SimpleDateFormat apptDateFormat = new SimpleDateFormat();
//        SimpleTimeZone apptTimeFormat = new SimpleTimeZone("HH:mm a");
//        LocalTime localTimeStart = LocalTime.parse(apptStartTime.getText(), DateTimeFormatter.ofPattern("hh:mm:ss a"));
//        LocalTime localTimeEnd = LocalTime.parse(apptEndTime.getText(), DateTimeFormatter.ofPattern("hh:mm:ss a"));

        //working
//        LocalTime localTimeStart = LocalTime.parse(apptStartTime.getText(), DateTimeFormatter.ofPattern("hh:mm"));
//        LocalTime localTimeEnd = LocalTime.parse(apptEndTime.getText(), DateTimeFormatter.ofPattern("hh:mm"));
//        LocalDate localDateStart = LocalDate.parse(datePickerApptStartDate.getValue().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        LocalDate localDateEnd = LocalDate.parse(datePickerApptEndDate.getValue().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        int id = Integer.parseInt(newApptID.getText());
        String title = apptTitle.getText();
        String desc = apptDesc.getText();
        String loc = apptLoc.getText();
        //create method to obtain integer from value of combobo
        int contactID = getContactIDFromName(comboBoxApptContact.getSelectionModel().getSelectedItem().toString());
        String contactName = comboBoxApptContact.getSelectionModel().getSelectedItem().toString();
        String type = comboBoxApptType.getSelectionModel().getSelectedItem().toString();
        String startTime = timeConversion(apptStartTime);
        String endTime = timeConversion(apptEndTime);
        String startDate = dateConversion(datePickerApptStartDate);
        String endDate = dateConversion(datePickerApptEndDate);

        System.out.println(startDate+" "+startTime);


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
    void onActionFill(ActionEvent event) {
        apptTitle.setText("titleFromButton");
        apptDesc.setText("descFromButton");
        apptLoc.setText("locFromButton");
        apptStartTime.setText("06:30 PM");
        apptEndTime.setText("08:30 PM");
        comboBoxApptContact.getSelectionModel().selectFirst();
        comboBoxApptType.getSelectionModel().selectFirst();
        comboBoxApptCustID.getSelectionModel().selectFirst();
        comboBoxApptUserID.getSelectionModel().selectFirst();

    }

    @FXML
    void onActionTest(ActionEvent event) {
        System.out.println("start time: " + timeConversion(apptStartTime));
        System.out.println("end time: " + timeConversion(apptEndTime));
        System.out.println("start date: " + dateConversion(datePickerApptStartDate));
        System.out.println("end date: " + dateConversion(datePickerApptEndDate));

        //        System.out.println("Original start date: " + datePickerApptStartDate.getValue());


//        LocalDate myDateStart = datePickerApptStartDate.getValue();
//        LocalDate myDateEnd = datePickerApptEndDate.getValue();
//        System.out.println(myDateStart.toString());
//        System.out.println(myDateEnd.toString());

//        onActionStartDate();
//        onActionEndDate();

    }

    public void onActionStartDate(ActionEvent event) {
//        try {
//            LocalDate myDateStart = datePickerApptStartDate.getValue();
//            System.out.println(myDateStart.toString());
//        } catch (Exception e) {
//            String myTextStart = datePickerApptStartDate.getEditor().getText();
//            System.out.println(myTextStart);
//        }
    }


    public void onActionEndDate(ActionEvent event) {
//        try {
//        LocalDate myDateEnd = datePickerApptEndDate.getValue();
//        System.out.println(myDateEnd.toString());
//        } catch (Exception e) {
//            String myTextEnd = datePickerApptEndDate.getEditor().getText();
//            System.out.println(myTextEnd);
//        }
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
