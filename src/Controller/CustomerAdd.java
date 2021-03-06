package Controller;

import DAO.CustomerQuery;
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

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/** CustomerAdd. Sets the scene for the Add Customer Screen
 */
public class CustomerAdd implements Initializable {

    /**The Stage.
     */
    Stage stage;
    /**The Scene.
     */
    Parent scene;

    @FXML
    private ComboBox comboBoxCountry = new ComboBox();

    @FXML
    private ComboBox comboBoxDivision = new ComboBox();

    @FXML
    private Text newCustomerID;

    @FXML
    private TextField custAddy;

    @FXML
    private TextField custName;

    @FXML
    private TextField custPhone;

    @FXML
    private TextField custPostal;

    /**The Error messages.
     * This array compiles a list of errors collected upon new customer creation attempt
     * The results will be output to the user for correction before being allowed
     * to create a customer
     */
    ArrayList<String> errorMessages = new ArrayList<String>();

    /**Add Error Messages
     * Entry errors detected call this method to provide details to the
     * list of compiled errors that will be presented to the user
     * @param errorMessage
     * @param type
     */
    private void errorMessagesAdd(String errorMessage, String type){

        if (type == "empty") {
            errorMessages.add(errorMessage + " field cannot be empty");
        }
        if (type == "not selected") {
            errorMessages.add("Please selected a country and division");
        }
    }


    /**Get total errors. Returns the list of collected error messages
     * @return
     */
    private ArrayList getErrorMessagesTotal() {
        return errorMessages;
    }

    /**Clear errors. Clears the list of error messages for customer creation reattempt
     */
    private void clearErrorMessages() {
        errorMessages.clear();
    }


    /**On action add customer. Gather data from input to create customer
     * Parse data from entry fields
     * Confirm all data is valid and not null
     * Confirm with user with details of the customer
     * to be created
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        Alert alertConfirmCustomerCreation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmCustomerCreation.setTitle("Add new customer");
        alertConfirmCustomerCreation.setHeaderText("Add new customer");
        alertConfirmCustomerCreation.setContentText("Do you want to add customer " + custName.getText() + " to the database?");
        Optional<ButtonType> result = alertConfirmCustomerCreation.showAndWait();
        if (result.get() == ButtonType.OK) {
            int id = Integer.parseInt(newCustomerID.getText());

            if (custName.getText().isEmpty()) {
                errorMessagesAdd("Customer Name", "empty");
            }
            String name = custName.getText();

            if (custAddy.getText().isEmpty()) {
                errorMessagesAdd("Customer Address", "empty");
            }
            String addy = custAddy.getText();

            if (custPostal.getText().isEmpty()) {
                errorMessagesAdd("Postal Code", "empty");
            }
            String postal = custPostal.getText();

            if (custPhone.getText().isEmpty()) {
                errorMessagesAdd("Customer Phone", "empty");
            }
            String phone = custPhone.getText();


            int div = 0;
            try {
                div = CustomerQuery.getDivisionIDFromComboBox(comboBoxDivision.getSelectionModel().getSelectedItem().toString());
            } catch (Exception noSelection) {
                errorMessagesAdd("Customer Location", "not selected");
            }

            if (!(getErrorMessagesTotal().size() == 0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setHeaderText("Error");
                alert.setContentText(String.join("\n", getErrorMessagesTotal()));
                alert.showAndWait();
                clearErrorMessages();
            } else {

                //If no entry errors, send parsed entries to addCustomer for insert into database

                CustomerQuery.addCustomer(new CustomerModel(id, name, addy, postal, phone, div, null, 0, null));

                Alert alertConfirmCustomerIsAdded = new Alert(Alert.AlertType.INFORMATION);
                alertConfirmCustomerIsAdded.setTitle("Add customer");
                alertConfirmCustomerIsAdded.setHeaderText("Add customer");
                alertConfirmCustomerIsAdded.setContentText(name + " has been added to the database.");
                Optional<ButtonType> result2 = alertConfirmCustomerIsAdded.showAndWait();

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/CustomersScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Customers");
                stage.show();
            }
        }

    }

    /**On action cancel. Confirm cancellation with user and cancel
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
            scene = FXMLLoader.load(getClass().getResource("/View/CustomersScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Customers");
            stage.show();
        }
    }


    /**Initialize.
     */
    @FXML
    void initialize() {

    }

    /**
     * initialize. initializes comboBoxes
     * Lambda listed below to update Update Division List. The lambda updates the division list
     * whenever either the comboBox for countries or the comboBox for divisions is clicked. Without
     * this function it was possible that the user could choose invalid divisions that did not match
     * the country. By running this lambda, i am able to ensure that the user will only ever choose
     * valid division list options.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxCountry.getItems().setAll(CustomerQuery.getAllCountries());

        /**Update Division List. lambda to update division list based on selected country.
         * prevents invalid data from being shown to user
         */
        comboBoxCountry.setOnMouseClicked((e -> updateDivisionList()));
        /**Update Division List. lambda to update division list based on selected country.
         * prevents invalid data from being shown to user
         */
        comboBoxDivision.setOnMouseClicked((e -> updateDivisionList()));
        //query next available customer ID

        newCustomerID.setText(String.valueOf(CustomerQuery.getHighestCustomerID()));
    }

    /**update division comboBox. updates combobox filtered by country
     * Lambda listed above to update Update Division List. The lambda updates the division list
     * whenever either the comboBox for countries or the comboBox for divisions is clicked. Without
     * this function it was possible that the user could choose invalid divisions that did not match
     * the country. By running this lambda, i am able to ensure that the user will only ever choose
     * valid division list options.
     */
    private void updateDivisionList(){
        try {
            String currentCountry = (String) comboBoxCountry.getSelectionModel().getSelectedItem();
            comboBoxDivision.getItems().setAll(CustomerQuery.getFilteredDivisions(currentCountry));
        }
        catch (Exception e) {
          //Catch exception cause by invalid selection
        }
    }
}
