package Controller;

import DAO.AppointmentQuery;
import DAO.CustomerQuery;
import Model.AppointmentModel;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static DAO.AppointmentQuery.getAllAppointmentsForCustomer;
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



    static ArrayList<String> formattingErrors = new ArrayList<String>();
    static String scheduleErrors = null;


    public static void formatErrorsAddMessage(String errorMessage, String type) {

        if (type == "empty") {
            formattingErrors.add(errorMessage + " field cannot be empty");
        }

        if (type == "format") {
            formattingErrors.add(errorMessage + " needs to be formatted correctly. Please see examples");
        }

        if (type == "dateTime") {
            formattingErrors.add(errorMessage);
        }



    }

    public static void scheduleErrorsSetMessage(String type) {

        if (type == "endBeforeStart") {
            scheduleErrors = "End of appointment must come after the start of the appointment";
        }

        if (type == "officeClosed") {
            scheduleErrors = "The office will closed at the requested time and date of your appointment";
        }

        if (type == "startOverlap") {
            scheduleErrors = "The start time of your requested appointment conflicts with an existing appointment";
        }

        if (type == "endOverlap") {
            scheduleErrors = "The end time of your requested appointment conflicts with an existing appointment";
        }

        if (type == "startEndOverlap") {
            scheduleErrors = "There is another appointment already at the requested time";
        }

    }







    private void clearFormatErrorMessages() {
        formattingErrors.clear();
    }

    private void clearScheduleErrorMessages() {
        scheduleErrors = null;
    }









    public static LocalDateTime dateTimeConversion(DatePicker dateInput, TextField timeInput) {
        String errorType = null;
        LocalDateTime convertedDateTime = null;
        LocalDate myInputDate = null;
        LocalTime myInputTime = null;
        if (dateInput.getValue() != null) {
            LocalDate tempInputDateValue = dateInput.getValue();
        }
        if (!dateInput.getEditor().getText().isEmpty()) {
            String tempInputDateText = dateInput.getEditor().getText();
        }


        //see if there is text, if so: parse it
        if (!dateInput.getEditor().getText().isEmpty()) {
            try {
                String myTextDate = dateInput.getEditor().getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/[uuuu][uu]");
                myInputDate = LocalDate.parse(myTextDate, formatter);
                //reset value incase user had selected a date, deleted it, and then type in a date
                dateInput.setValue(null);
                dateInput.getEditor().setText(myInputDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            } catch (Exception d) {
//                errorType = "Error in date input";
                //there was inparsable text
                formatErrorsAddMessage("Error in date input (via text input)", "dateTime");
            }

            //obtain value of date from date picker
        } else if (dateInput.getValue() != null) {
            try {
                myInputDate = dateInput.getValue();
                dateInput.getEditor().setText(myInputDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            } catch (Exception datePickerError) {
                formatErrorsAddMessage("Error in date input (via Date Picker input)", "dateTime");
            }
        } else {
//            System.out.println("Null input for date");
            formatErrorsAddMessage("Date entry is empty", "dateTime");
        }


        //convert time from time input

        try {
            DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("h:mm[ ][]a");
            DateTimeFormatter convertFormat = DateTimeFormatter.ofPattern("H:mm:ss");

            String correctCaps = timeInput.getText().replace("am", "AM").replace("pm", "PM");
            myInputTime = LocalTime.parse(correctCaps, parseFormat);
        } catch (Exception e) {
//            errorType = "Error in time input";
            formatErrorsAddMessage("Error in time input", "dateTime");
        }


        if (myInputDate != null && myInputTime != null) {
            convertedDateTime = LocalDateTime.of(myInputDate, myInputTime);
        }


        return convertedDateTime;
    }

    public void validateAppointments(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer customerID) {


        //ensure appointment ends after it starts
        if (!(startDateTime.compareTo(endDateTime) < 0)) {
            scheduleErrorsSetMessage("endBeforeStart");
            return;
        }


        //ensure appointment is during business hours

        //<editor-fold desc="Description">
        //setup checks for start of appointment
        ZonedDateTime localZoneStartOfAppointment = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localZoneEndOfAppointment = endDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime targetZoneStartOfAppointment = localZoneStartOfAppointment.withZoneSameInstant(ZoneId.of("US/Eastern"));
        ZonedDateTime targetZoneEndOfAppointment = localZoneEndOfAppointment.withZoneSameInstant(ZoneId.of("US/Eastern"));
        LocalDateTime localizedAppointmentStartTime = targetZoneStartOfAppointment.toLocalDateTime();
        LocalDateTime localizedAppointmentEndTime = targetZoneEndOfAppointment.toLocalDateTime();

        //parse the date of the appointment for use later
        LocalDate localDate = localizedAppointmentStartTime.toLocalDate();
        LocalTime localOpeningHours = LocalTime.of(8,0);
        LocalTime localClosingHours = LocalTime.of(22,0);
        LocalDateTime localOpeningTime = LocalDateTime.of(localDate,localOpeningHours);
        LocalDateTime localClosingTime = LocalDateTime.of(localDate,localClosingHours);

        long timeDiffStart = ChronoUnit.MINUTES.between(localOpeningTime,localizedAppointmentStartTime);
        long timeDiffClose = ChronoUnit.MINUTES.between(localizedAppointmentEndTime, localClosingTime);

        if (timeDiffStart < 0 | timeDiffClose < 0) {
        scheduleErrorsSetMessage("officeClosed");
        return;
    }
        //</editor-fold>





        //ensure appointment does not conflict with any other appointment

        ArrayList<AppointmentModel> comparisonArray = getAllAppointmentsForCustomer(customerID);
        for (AppointmentModel A : comparisonArray) {

            LocalDateTime OS = A.getStartDateTime();
            LocalDateTime OE = A.getEndDateTime();
            LocalDateTime NS = startDateTime;
            LocalDateTime NE = endDateTime;

            boolean startOverlap = ( ( NS.isAfter(OS) || NS.isEqual(OS ) ) && NS.isBefore(OE) );
            boolean endOverlap = ( NE.isAfter(OS) && ( NE.isEqual(OE) || NE.isBefore(OE) ) );
            boolean startEndOverlap = ( ( NS.isBefore(OS) || NS.isEqual(OS) ) && (NE.isAfter(OE) || NE.isEqual(OE) ) );


            if(startEndOverlap) {
                scheduleErrorsSetMessage("startEndOverlap");
                return;
            }

            if(startOverlap) {
            scheduleErrorsSetMessage("startOverlap");
            return;
            }

            if(endOverlap) {
                scheduleErrorsSetMessage("endOverlap");
                return;
            }


        }
    }



    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {


        int id = Integer.parseInt(newApptID.getText());

        boolean existingErrors = false;

        if (apptTitle.getText().isEmpty()) {
            formatErrorsAddMessage("Title", "empty");
        }
        String title = apptTitle.getText();

        if (apptDesc.getText().isEmpty()) {
            formatErrorsAddMessage("Description", "empty");
        }
        String desc = apptDesc.getText();


        if (apptLoc.getText().isEmpty()) {
            formatErrorsAddMessage("Location", "empty");
        }
        String loc = apptLoc.getText();


        int contactID = 0;
        String contactName = null;

        try {
            contactID = getContactIDFromName(comboBoxApptContact.getSelectionModel().getSelectedItem().toString());
            contactName = comboBoxApptContact.getSelectionModel().getSelectedItem().toString();
        } catch (Exception entryError) {
            formatErrorsAddMessage("Contact Name", "empty");
        }

        String type = null;
        try {
            type = comboBoxApptType.getSelectionModel().getSelectedItem().toString();
        } catch (Exception entryError) {
            formatErrorsAddMessage("Type", "empty");
        }


        LocalDateTime startDateTime = null;
        try {
            startDateTime = dateTimeConversion(datePickerApptStartDate, apptStartTime);
        } catch (Exception entryError) {
        }

        LocalDateTime endDateTime = null;
        try {
            endDateTime = dateTimeConversion(datePickerApptEndDate, apptEndTime);
        } catch (Exception entryError) {
        }


        int customerID = 0;
        try {
            customerID = Integer.parseInt(comboBoxApptCustID.getSelectionModel().getSelectedItem().toString());
        } catch (Exception entryError) {
            formatErrorsAddMessage("Customer ID", "empty");
        }

        int userID = 0;
        try {
            userID = Integer.parseInt(comboBoxApptUserID.getSelectionModel().getSelectedItem().toString());
        } catch (Exception entryError) {
            formatErrorsAddMessage("User ID", "empty");
        }

        if (startDateTime != null && endDateTime != null && customerID != 0) {
            validateAppointments(startDateTime, endDateTime, customerID);
        }

        if (!formattingErrors.isEmpty()) {
            Alert formatAlert = new Alert(Alert.AlertType.ERROR);
            formatAlert.setTitle("Input Error");
            formatAlert.setHeaderText("Error");
            formatAlert.setContentText(String.join("\n", formattingErrors));
            formatAlert.showAndWait();
            clearFormatErrorMessages();
        } else if (scheduleErrors != null) {
            Alert scheduleAlert = new Alert(Alert.AlertType.ERROR);
            scheduleAlert.setTitle("Scheduling Error");
            scheduleAlert.setHeaderText("Error");
            scheduleAlert.setContentText(scheduleErrors);
            scheduleAlert.showAndWait();
            clearScheduleErrorMessages();
        } else {
            String customerName = CustomerQuery.getCurrentCustomer(customerID).getCustomerName();


            Alert alertConfirmAppointmentCreation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmAppointmentCreation.setTitle("Add new appointment");
            alertConfirmAppointmentCreation.setHeaderText("Add new appointment");
            alertConfirmAppointmentCreation.setContentText("Do you want to create the following appointment? " + "\n" +
                    "\n Customer Name: " + customerName +
                    "\n Title: " + title +
                    "\n Description: " + desc +
                    "\n Location: " + loc +
                    "\n Contact Name: " + contactName +
                    "\n Type: " + type +
                    "\n Start Time: " + startDateTime.format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a ")) + TimeZone.getDefault().getID() +
                    "\n End Time: " + endDateTime.format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a ")) + TimeZone.getDefault().getID() +
                    "\n Customer ID: " + customerID +
                    "\n User ID: " + userID +
                    "\n Contact ID: " + contactID);
            Optional<ButtonType> result = alertConfirmAppointmentCreation.showAndWait();
            if (result.get() == ButtonType.OK) {


                AppointmentQuery.addAppointment(new AppointmentModel(id, title, desc, loc, contactName, type, startDateTime, endDateTime, customerID, userID, contactID));


                Alert alertConfirmAppointmentIsAdded = new Alert(Alert.AlertType.INFORMATION);
                alertConfirmAppointmentIsAdded.setTitle("Add appointment");
                alertConfirmAppointmentIsAdded.setHeaderText("Add appointment");
                alertConfirmAppointmentIsAdded.setContentText("Appointment for " + customerName + " has been scheduled.");
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
    void onActionFillAllButDate(ActionEvent event) {
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
    void onActionFillAllButTime(ActionEvent event) {
        apptTitle.setText("titleFromButton");
        apptDesc.setText("descFromButton");
        apptLoc.setText("locFromButton");

        comboBoxApptContact.getSelectionModel().selectFirst();
        comboBoxApptType.getSelectionModel().selectFirst();
        comboBoxApptCustID.getSelectionModel().selectFirst();
        comboBoxApptUserID.getSelectionModel().selectFirst();
        datePickerApptStartDate.setValue(LocalDate.now());
        datePickerApptEndDate.setValue(LocalDate.now());
    }

    @FXML
    void onActionFillAllButDateTime(ActionEvent event) {
        apptTitle.setText("titleFromButton");
        apptDesc.setText("descFromButton");
        apptLoc.setText("locFromButton");

        comboBoxApptContact.getSelectionModel().selectFirst();
        comboBoxApptType.getSelectionModel().selectFirst();
        comboBoxApptCustID.getSelectionModel().selectFirst();
        comboBoxApptUserID.getSelectionModel().selectFirst();


    }

    @FXML
    void onActionTest(ActionEvent event) {
        LocalDateTime startDateTime = null;
        try {
            startDateTime = dateTimeConversion(datePickerApptStartDate, apptStartTime);
        } catch (Exception entryError) {
        }
        System.out.println(startDateTime);

        LocalDateTime endDateTime = null;
        try {
            endDateTime = dateTimeConversion(datePickerApptEndDate, apptEndTime);
        } catch (Exception entryError) {
        }
        System.out.println(endDateTime);

        if ((startDateTime != null & endDateTime != null)) {

            if (!(startDateTime.compareTo(endDateTime) < 0)) {
                //good value
//                errorMessagesadd("End date and time of appointment must come later than the start date and time", "dateTime");
            }

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
