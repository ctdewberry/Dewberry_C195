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

public class CustomerModify implements Initializable {

    Stage stage;
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

    public void sendCustomer(CustomerModel CustomerModel)
    {
//        System.out.println(currentCustomer);
//        System.out.println(CustomerQuery.getCurrentCustomer(currentCustomer).getCustomerName());
        currentCustomerID.setText(String.valueOf(CustomerModel.getCustomerID()));
        custAddy.setText(String.valueOf(CustomerModel.getCustomerAddress()));
        custName.setText(String.valueOf(CustomerModel.getCustomerName()));
        custPhone.setText(String.valueOf(CustomerModel.getCustomerPhone()));
        custPostal.setText(String.valueOf(CustomerModel.getCustomerCode()));
        comboBoxCountry.getSelectionModel().select(String.valueOf(CustomerModel.getCustomerCountry()));
        comboBoxDivision.getSelectionModel().select(String.valueOf(CustomerModel.getCustomerDivision()));
    }


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
    void onActionUpdateCustomer(ActionEvent event) throws IOException{
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



//            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//            scene = FXMLLoader.load(getClass().getResource("/View/CustomersScreen.fxml"));
//            stage.setScene(new Scene(scene));
//            stage.setTitle("Customers");
//            stage.show();

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
        comboBoxCountry.setOnMouseClicked((e -> updateDivisionList()));
        comboBoxDivision.setOnMouseClicked((e -> updateDivisionList()));

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
