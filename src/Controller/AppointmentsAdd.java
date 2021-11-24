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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

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




//    public static String dateConversion(DatePicker dateInput) {
//
//        String dateOutput = null;
//        try {
//            LocalDate myInputDate = dateInput.getValue();
//            dateOutput = myInputDate.toString();
//            dateInput.setValue(null);
//            dateInput.getEditor().setText(dateOutput);
//        } catch (Exception e) {
//            try {
//                String myTextStart = dateInput.getEditor().getText();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/[uuuu][uu]");
//                LocalDate date = LocalDate.parse(myTextStart, formatter);
//                dateOutput = date.toString();
//            } catch (Exception d) {
//            }
//        }
//        return dateOutput;
//    }
//
//    public static String timeConversion(TextField timeInput) {
//        String timeOutput = null;
//
//        try {
//
//            DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("h:mm[ ][]a");
//            DateTimeFormatter convertFormat = DateTimeFormatter.ofPattern("H:mm:ss");
//
//            String correctCaps = timeInput.getText().replace("am","AM").replace("pm","PM");
//            LocalTime parsedInput = LocalTime.parse(correctCaps, parseFormat);
//            String convertedInput = convertFormat.format(parsedInput);
//            timeOutput = convertedInput;
//        } catch (Exception e) {
//        }
//        return timeOutput;
//    }

//    public static String dateTimeConversion(DatePicker dateInput, TextField timeInput){
//        String errorType = null;
//        String convertedDateTime = null;
//        LocalDate myInputDate = null;
//        LocalTime myInputTime = null;
//
//        try {
//            myInputDate = dateInput.getValue();
//            dateInput.setValue(null);
//            dateInput.getEditor().setText(myInputDate.toString());
//        } catch (Exception e) {
//            try {
//                String myTextDate = dateInput.getEditor().getText();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/[uuuu][uu]");
//                myInputDate = LocalDate.parse(myTextDate, formatter);
//            } catch (Exception d) {
//                errorType = "Error in date input";
//            }
//        }
//        try {
//        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("h:mm[ ][]a");
//        DateTimeFormatter convertFormat = DateTimeFormatter.ofPattern("H:mm:ss");
//
//        String correctCaps = timeInput.getText().replace("am","AM").replace("pm","PM");
//        myInputTime = LocalTime.parse(correctCaps, parseFormat);
//    } catch (Exception e) {
//            errorType = "Error in time input";
//    }
//
//        LocalDateTime dateTime = LocalDateTime.of(myInputDate, myInputTime);
//        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of("UTC"));
//        convertedDateTime = zonedDateTime.toString();
//
//        if (errorType == null) {
//            return convertedDateTime;
//        } else {
//            return errorType;
//        }
//    }


    static ArrayList<String> errorMessages = new ArrayList<String>();

    public static void errorMessagesAdd(String errorMessage, String type){

        if (type == "empty") {
            errorMessages.add(errorMessage + " field cannot be empty");
        }

        if (type == "format") {
            errorMessages.add(errorMessage + " needs to be formatted correctly. Please see examples");
        }

        if (type == "dateTime") {
            errorMessages.add(errorMessage);
        }

    }


    private ArrayList getErrorMessagesTotal() {
        return errorMessages;
    }

    private void clearErrorMessages() {
        errorMessages.clear();
    }

    public static LocalDateTime dateTimeConversion(DatePicker dateInput, TextField timeInput){
        String errorType = null;
        LocalDateTime convertedDateTime = null;
        LocalDate myInputDate = null;
        LocalTime myInputTime = null;

        try {
            myInputDate = dateInput.getValue();
            dateInput.setValue(null);
            dateInput.getEditor().setText(myInputDate.toString());
        } catch (Exception e) {
            try {
                String myTextDate = dateInput.getEditor().getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/[uuuu][uu]");
                myInputDate = LocalDate.parse(myTextDate, formatter);
            } catch (Exception d) {
//                errorType = "Error in date input";
                errorMessagesAdd("Error in date input","dateTime");
            }
        }


        try {
            DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("h:mm[ ][]a");
            DateTimeFormatter convertFormat = DateTimeFormatter.ofPattern("H:mm:ss");

            String correctCaps = timeInput.getText().replace("am","AM").replace("pm","PM");
            myInputTime = LocalTime.parse(correctCaps, parseFormat);
        } catch (Exception e) {
//            errorType = "Error in time input";
            errorMessagesAdd("Error in time input","timeTime");
        }

        convertedDateTime = LocalDateTime.of(myInputDate, myInputTime);
//        System.out.println(dateTime);
//        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
//        System.out.println(zonedDateTime);
//        Instant inst = dateTime.toInstant();
//        convertedDateTime = inst.toString();
//        System.out.println(convertedDateTime);


        return convertedDateTime;
    }


    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {


        String chosenType = "NO SELECTION";
        try {
        chosenType = comboBoxApptType.getSelectionModel().getSelectedItem().toString();}
        catch (Exception noSelection){

        }


        Alert alertConfirmAppointmentCreation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmAppointmentCreation.setTitle("Add new appointment");
        alertConfirmAppointmentCreation.setHeaderText("Add new appointment");
        alertConfirmAppointmentCreation.setContentText("Do you want to create an appointment of type: " + chosenType + "?");
        Optional<ButtonType> result = alertConfirmAppointmentCreation.showAndWait();
        if (result.get() == ButtonType.OK) {

            int id = Integer.parseInt(newApptID.getText());

            boolean existingErrors = false;

            if (apptTitle.getText().isEmpty()) {
                errorMessagesAdd("Title", "empty");
            }
            String title = apptTitle.getText();

            if (apptDesc.getText().isEmpty()) {
                errorMessagesAdd("Description", "empty");
            }
            String desc = apptDesc.getText();


            if (apptLoc.getText().isEmpty()) {
                errorMessagesAdd("Location", "empty");
            }
            String loc = apptLoc.getText();


            int contactID = 0;
            String contactName = null;

            try {
                contactID = getContactIDFromName(comboBoxApptContact.getSelectionModel().getSelectedItem().toString());
                contactName = comboBoxApptContact.getSelectionModel().getSelectedItem().toString();
            } catch (Exception entryError) {
                errorMessagesAdd("Contact Name", "empty");
            }

            String type = null;
            try {
                type = comboBoxApptType.getSelectionModel().getSelectedItem().toString();
            } catch (Exception entryError) {
                errorMessagesAdd("Type", "empty");
            }


//            String startTime = null;
//            String endTime = null;
//            String startDate = null;
//            String endDate = null;
//            try { startTime = timeConversion(apptStartTime);
//                if (startTime == null) { errorMessagesAdd("Start Time", "format");}
//            } catch (Exception entryError) {}
//            try { endTime = timeConversion(apptEndTime);
//                if (endTime == null) { errorMessagesAdd("End Time", "format");}
//                } catch (Exception entryError) { }
//            try { startDate = dateConversion(datePickerApptStartDate);
//                if (startDate == null) { errorMessagesAdd("Start Date", "format");}
//            } catch (Exception entryError) { }
//            try { endDate = dateConversion(datePickerApptEndDate);
//                if (endDate == null) { errorMessagesAdd("End Date", "format");}
//            } catch (Exception entryError) { }


            LocalDateTime startDateTime = null;
            try {
                startDateTime = dateTimeConversion(datePickerApptStartDate, apptStartTime);
            } catch (Exception entryError) {}
            System.out.println(startDateTime);

            LocalDateTime endDateTime = null;
            try {
                endDateTime = dateTimeConversion(datePickerApptEndDate, apptEndTime);
            } catch (Exception entryError) {}
            System.out.println(endDateTime);



            int customerID = 0;
            try {
                customerID = Integer.parseInt(comboBoxApptCustID.getSelectionModel().getSelectedItem().toString());
            } catch (Exception entryError) {
                errorMessagesAdd("Customer ID", "empty");
            }

            int userID = 0;
            try {
                userID = Integer.parseInt(comboBoxApptUserID.getSelectionModel().getSelectedItem().toString());
            } catch (Exception entryError) {
                errorMessagesAdd("User ID", "empty");
            }


            if (!(getErrorMessagesTotal().size() == 0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Error");
                alert.setContentText(String.join("\n", getErrorMessagesTotal()));
                alert.showAndWait();
                clearErrorMessages();
            } else {


//                System.out.println(userID);
                AppointmentQuery.addAppointment(new AppointmentModel(id, title, desc, loc, contactName, type, startDateTime, endDateTime, customerID, userID, contactID));


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
//        System.out.println("start time: " + timeConversion(apptStartTime));
//        System.out.println("end time: " + timeConversion(apptEndTime));
//        System.out.println("start date: " + dateConversion(datePickerApptStartDate));
//        System.out.println("end date: " + dateConversion(datePickerApptEndDate));

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
