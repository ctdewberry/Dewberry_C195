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

public class CustomerAdd implements Initializable {

    Stage stage;
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

    ArrayList<String> errorMessages = new ArrayList<String>();

    private void errorMessagesAdd(String errorMessage, String type){

        if (type == "empty") {
            errorMessages.add(errorMessage + " field cannot be empty");
        }
        if (type == "not selected") {
            errorMessages.add("Please selected a country and division");
        }
    }


    private ArrayList getErrorMessagesTotal() {
        return errorMessages;
    }

    private void clearErrorMessages() {
        errorMessages.clear();
    }


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


    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxCountry.getItems().setAll(CustomerQuery.getAllCountries());
        //lambda to update division list based on country selected. running lambda on both country and division clicked prevents a invalid division from being chosen
        comboBoxCountry.setOnMouseClicked((e -> updateDivisionList()));
        //lambda to update division list based on country selected
        comboBoxDivision.setOnMouseClicked((e -> updateDivisionList()));
        newCustomerID.setText(String.valueOf(CustomerQuery.getHighestCustomerID()));


    }

    private void updateDivisionList(){
        try {
            String currentCountry = (String) comboBoxCountry.getSelectionModel().getSelectedItem();
            comboBoxDivision.getItems().setAll(CustomerQuery.getFilteredDivisions(currentCountry));
        }
        catch (Exception e) {
        }
    }

}
