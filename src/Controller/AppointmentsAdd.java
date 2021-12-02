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

/**
 * AppointmentsAdd. Sets the scene for the Add Appointment screen
 */
public class AppointmentsAdd implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
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


    /**
     * Formatting errors. List of formatting errors.
     * This array compiles a list of errors collected upon new appointment creation attempt
     * The results will be output to the user for correction before being allowed
     * to create an appointment
     */
    static ArrayList<String> formattingErrors = new ArrayList<String>();

    /**
     * Clear Schedule errors. Clears Schedule errors.
     * Sets schedule errors to null until an error is detected
     */
    static String scheduleErrors = null;


    /**
     * Format errors add message. Add formatting errors to list.
     * Formatting errors detected call this method to provide details to the
     * list of compiled errors that will be presented to the user
     *
     * @param errorMessage the error message
     * @param type         the type
     */
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

    /**
     * Schedule errors set message. Add schedule errors to list
     * Scheduling errors detected call this method to provide details to
     * the list of compiled error that will be presented to the user
     *
     * @param type the type
     */
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


    /**
     * Clear format errors. Clears the list of error messages for appointment creation reattempt
     */

    private void clearFormatErrorMessages() {
        formattingErrors.clear();
    }

    /**
     * Clear schedule errors. Clears the list of error messages for appointment creation reattempt
     */
    private void clearScheduleErrorMessages() {
        scheduleErrors = null;
    }


    /**
     * Date time conversion. Parse and convert dateTime
     * Parses date and time input fields
     * Distinguishes between text and date picker
     * Once parsed it returns a local date time to be used
     * for appointment creation
     *
     * @param dateInput the date input
     * @param timeInput the time input
     * @return the local date time
     */
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


        //Checks date picker field for text. If found it parses the date

        if (!dateInput.getEditor().getText().isEmpty()) {
            try {
                String myTextDate = dateInput.getEditor().getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/[uuuu][uu]");
                myInputDate = LocalDate.parse(myTextDate, formatter);
                //Reset value of dateInput to rectify bug when users uses date picker to select
                //a date, deletes it, and then attempts to type in a date

                dateInput.setValue(null);
                dateInput.getEditor().setText(myInputDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            } catch (Exception d) {
                //Catches error in inputted text, text entered is unable to be parsed
                //Add error code to aggregated error messages

                formatErrorsAddMessage("Error in date input (via text input)", "dateTime");
            }

            //Attempt to obtain value of date when user used datepicker instead of entering text

        } else if (dateInput.getValue() != null) {
            try {
                myInputDate = dateInput.getValue();
                dateInput.getEditor().setText(myInputDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            } catch (Exception datePickerError) {
                //Catches error in attempting to extract date picker date

                formatErrorsAddMessage("Error in date input (via Date Picker input)", "dateTime");
            }
        } else {
            //No information is in date picker field. Return error of an empty field

            formatErrorsAddMessage("Date entry is empty", "dateTime");
        }


        //Attempt parsing of entered time, allowing for use of various commonly used
        //formats when entering

        try {
            DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("h:mm[ ][]a");
            DateTimeFormatter convertFormat = DateTimeFormatter.ofPattern("H:mm:ss");

            String correctCaps = timeInput.getText().replace("am", "AM").replace("pm", "PM");
            myInputTime = LocalTime.parse(correctCaps, parseFormat);
        } catch (Exception e) {
            //Catches exception, unable to parse entered time

            formatErrorsAddMessage("Error in time input", "dateTime");
        }


        //If both date and time pass all parsing requirements, combine to create a localDateTime and return

        if (myInputDate != null && myInputTime != null) {
            convertedDateTime = LocalDateTime.of(myInputDate, myInputTime);
        }


        return convertedDateTime;
    }


    /**
     * Validate appointments. Validate appointments based on various criteria
     *
     * @param startDateTime the start date time
     * @param endDateTime   the end date time
     * @param customerID    the customer id
     */
    public void validateAppointments(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer customerID) {

        //Method will not return anything, but will add to aggregated error list if there are any issues
        //notifying the user of what needs to be corrected


        //Validation 1. Ensure end date of appointment comes after start date

        if (!(startDateTime.compareTo(endDateTime) < 0)) {
            scheduleErrorsSetMessage("endBeforeStart");
            return;
        }


        //Validation 2. Ensure appointment is scheduled during business hours
        //Prepare variables for use in checking business hours
        //Convert requested appointment date to zoned date time of the business (EST)
        // and convert to local date time for comparison


        ZonedDateTime localZoneStartOfAppointment = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime localZoneEndOfAppointment = endDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime targetZoneStartOfAppointment = localZoneStartOfAppointment.withZoneSameInstant(ZoneId.of("US/Eastern"));
        ZonedDateTime targetZoneEndOfAppointment = localZoneEndOfAppointment.withZoneSameInstant(ZoneId.of("US/Eastern"));
        LocalDateTime localizedAppointmentStartTime = targetZoneStartOfAppointment.toLocalDateTime();
        LocalDateTime localizedAppointmentEndTime = targetZoneEndOfAppointment.toLocalDateTime();

        //Parse and convert business operating hours to local date time for comparison

        LocalDate localDate = localizedAppointmentStartTime.toLocalDate();
        LocalTime localOpeningHours = LocalTime.of(8, 0);
        LocalTime localClosingHours = LocalTime.of(22, 0);
        LocalDateTime localOpeningTime = LocalDateTime.of(localDate, localOpeningHours);
        LocalDateTime localClosingTime = LocalDateTime.of(localDate, localClosingHours);

        // Compare operating hours of business with requested appointment time.
        // Return appropriate error


        long timeDiffStart = ChronoUnit.MINUTES.between(localOpeningTime, localizedAppointmentStartTime);
        long timeDiffClose = ChronoUnit.MINUTES.between(localizedAppointmentEndTime, localClosingTime);

        if (timeDiffStart < 0 | timeDiffClose < 0) {
            scheduleErrorsSetMessage("officeClosed");
            return;
        }


        //Validation 3. Compare requested appointment time with any other appointments for the same customer


        ArrayList<AppointmentModel> comparisonArray = getAllAppointmentsForCustomer(customerID);
        for (AppointmentModel A : comparisonArray) {

            // Variables condensed for easier handling
            // ES = [E]xisting [S]tart - Start DateTime of existing appointments
            // EE = [E]xisting [E]nd - End DateTime of existing appointments
            // NS = [N]ew [S]tart - Start DateTime of requested appointment
            // NE = [N]ew [E]nd - End DateTime of requested appointment


            LocalDateTime ES = A.getStartDateTime();
            LocalDateTime EE = A.getEndDateTime();
            LocalDateTime NS = startDateTime;
            LocalDateTime NE = endDateTime;

            //Check to see if requested appointment will start during an existing appointment

            boolean startOverlap = ((NS.isAfter(ES) || NS.isEqual(ES)) && NS.isBefore(EE));
            //Check to see if requested appointment will end during an existing appointment

            boolean endOverlap = (NE.isAfter(ES) && (NE.isEqual(EE) || NE.isBefore(EE)));
            //Check to see if requested appointment will overlap an existing appointment

            boolean startEndOverlap = ((NS.isBefore(ES) || NS.isEqual(ES)) && (NE.isAfter(EE) || NE.isEqual(EE)));


            //Return the appropriate error from the previous comparisons

            if (startEndOverlap) {
                scheduleErrorsSetMessage("startEndOverlap");
                return;
            }

            if (startOverlap) {
                scheduleErrorsSetMessage("startOverlap");
                return;
            }

            if (endOverlap) {
                scheduleErrorsSetMessage("endOverlap");
                return;
            }
        }
    }


    /**
     * On action add appointment. Gather input to add appointment.
     * Parses data from entered fields to sent to AddAppointment query
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        //Check all fields for empty or erroneous data


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


        Boolean noText = true;
        if (comboBoxApptType.getEditor().getText().isEmpty()) {
            try {
                type = comboBoxApptType.getSelectionModel().getSelectedItem().toString();
                noText = false;
                if (type.isEmpty()) {
                    noText = true;
                }
            } catch (Exception entryError) {
                noText = true;
            }
        } else {
            type = comboBoxApptType.getEditor().getText();
            noText = false;
        }

        if (noText) {
            formatErrorsAddMessage("Type", "empty");
        }

        //Send dateTime fields to be parsed for validation


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

        //If dateTimes were successfully parsed, send them to be validated


        if (startDateTime != null && endDateTime != null && customerID != 0) {
            validateAppointments(startDateTime, endDateTime, customerID);
        }

        //Test to see if any errors collected
        //If so, alert the user of what needs to be corrected


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


            //If all data is valid, prompt the user with confirmation of the data
            //collected and confirm they wish to create an appointment with entered info


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
                    "\n Start Time: " + startDateTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a z")) +
                    "\n End Time: " + endDateTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MM/dd/yy hh:mm a z")) +
                    "\n Customer ID: " + customerID +
                    "\n User ID: " + userID +
                    "\n Contact ID: " + contactID);
            Optional<ButtonType> result = alertConfirmAppointmentCreation.showAndWait();
            if (result.get() == ButtonType.OK) {

                //Upon confirmation, send the data to addAppointment query for database insert

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


    /**
     * On action cancel. Cancel appoint add.
     * Prompt for confirmation
     *
     * @param event the event
     * @throws IOException the io exception
     */
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


    /**
     * Initialize.
     */
    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Populate combo boxes

        comboBoxApptContact.getItems().setAll(AppointmentQuery.getAllContacts());
        comboBoxApptType.getItems().setAll(AppointmentQuery.getAllTypes());
        comboBoxApptCustID.getItems().setAll(AppointmentQuery.getAllCustomerIDs());
        comboBoxApptUserID.getItems().setAll(AppointmentQuery.getAllUserIDs());
        //query next available appointment ID

        newApptID.setText(String.valueOf(AppointmentQuery.getHighestAppointmentID()));
    }
}
