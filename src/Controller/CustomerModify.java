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

import java.awt.event.*;

import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The type Customer modify.
 */
public class CustomerModify implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private ComboBox comboBoxCountry = new ComboBox();

    @FXML
    private ComboBox comboBoxDivision = new ComboBox();

    @FXML
    private Text currentCustomerID;

    @FXML
    private TextField custAddy;

    @FXML
    private TextField custName;

    @FXML
    private TextField custPhone;

    @FXML
    private TextField custPostal;

    /**
     * Send customer.
     * Obtains details from selected customer to prepoulate
     * the modify customer screen
     *
     * @param CustomerModel the customer model
     */
    public void sendCustomer(CustomerModel CustomerModel) {
        currentCustomerID.setText(String.valueOf(CustomerModel.getCustomerID()));
        custAddy.setText(String.valueOf(CustomerModel.getCustomerAddress()));
        custName.setText(String.valueOf(CustomerModel.getCustomerName()));
        custPhone.setText(String.valueOf(CustomerModel.getCustomerPhone()));
        custPostal.setText(String.valueOf(CustomerModel.getCustomerCode()));
        comboBoxCountry.getSelectionModel().select(String.valueOf(CustomerModel.getCustomerCountry()));
        comboBoxDivision.getSelectionModel().select(String.valueOf(CustomerModel.getCustomerDivision()));
    }


    /**
     * The Error messages.
     * This array compiles a list of errors collected upon customer update attempt
     * The results will be output to the user for correction before being allowed
     * to modify a customer
     */
    ArrayList<String> errorMessages = new ArrayList<String>();

    /**
     * Add Error Messages
     * Entry errors detected call this method to provide details to the
     * list of compiled errors that will be presented to the user
     *
     * @param errorMessage
     * @param type
     */
    private void errorMessagesAdd(String errorMessage, String type) {

        if (type == "empty") {
            errorMessages.add(errorMessage + " field cannot be empty");
        }
        if (type == "not selected") {
            errorMessages.add("Please selected a country and division");
        }
    }

    /**
     * Returns the list of collected error messages to be presented
     * to the user for correction
     *
     * @return
     */
    private ArrayList getErrorMessagesTotal() {
        return errorMessages;
    }

    /**
     * Clears the list of error messages for customer update reattempt
     */
    private void clearErrorMessages() {
        errorMessages.clear();
    }


    /**
     * On action update customer.
     * Parse data from entry fields
     * Confirm all data is valid and not null
     * Confirm with user with details of the customer
     * to be update
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {
        Alert alertConfirmCustomerModify = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmCustomerModify.setTitle("Modify Customer Info");
        alertConfirmCustomerModify.setHeaderText("Modify Customer Info");
        alertConfirmCustomerModify.setContentText("Do you want to update the information for this customer?");
        Optional<ButtonType> result = alertConfirmCustomerModify.showAndWait();
        if (result.get() == ButtonType.OK) {
            int id = Integer.parseInt(currentCustomerID.getText());

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

                /**
                 * If no entry errors, send parsed entries to modifyCustomer for update of database
                 */
                CustomerQuery.modifyCustomer(new CustomerModel(id, name, addy, postal, phone, div, null, 0, null));

                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/CustomersScreen.fxml"));
                    loader.load();
                    CustomersScreen CustomerScreen = loader.getController();
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Parent scene = loader.getRoot();
                    stage.setScene(new Scene(scene));
                    stage.setTitle("Customers");
                    stage.show();
                } catch (NullPointerException e) {
                    return;
                }
            }


        }
    }

    /**
     * On action cancel.
     * Confirm with user that they want to cancel modifying a customer
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
            scene = FXMLLoader.load(getClass().getResource("/View/CustomersScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Customers");
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
        comboBoxCountry.getItems().setAll(CustomerQuery.getAllCountries());

        /**
         * lambda to update division list based on country selected.
         * running lambda on both country and division comboBox clicks prevents an
         * invalid division from being shown to user
         */
        comboBoxCountry.setOnMouseClicked((e -> updateDivisionList()));
        /**
         * lambda to update division comboBox option based on the selected country
         * this ensures that the user only sees valid options
         */
        comboBoxDivision.setOnMouseClicked((e -> updateDivisionList()));

    }

    /**
     * update division comboBox based on country selected
     * send country to getFilteredDivisions query and set division list based
     * on resulting observableList
     */
    private void updateDivisionList() {
        try {
            String currentCountry = (String) comboBoxCountry.getSelectionModel().getSelectedItem();
            comboBoxDivision.getItems().setAll(CustomerQuery.getFilteredDivisions(currentCountry));
        }
        catch (Exception e) {
            /**
             * Catch exception cause by invalid selection
             */
        }
    }
}
