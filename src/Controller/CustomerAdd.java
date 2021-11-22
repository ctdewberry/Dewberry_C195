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

    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        Alert alertConfirmCustomerCreation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmCustomerCreation.setTitle("Add new customer");
        alertConfirmCustomerCreation.setHeaderText("Add new customer");
        alertConfirmCustomerCreation.setContentText("Do you want to add customer " + custName.getText() + " to the database?");
        Optional<ButtonType> result = alertConfirmCustomerCreation.showAndWait();
        if (result.get() == ButtonType.OK) {
            int id = Integer.parseInt(newCustomerID.getText());
            String name = custName.getText();
            String addy = custAddy.getText();
            String postal = custPostal.getText();
            String phone = custPhone.getText();
            int div = CustomerQuery.getDivisionIDFromComboBox(comboBoxDivision.getSelectionModel().getSelectedItem().toString());
            CustomerQuery.addCustomer(new CustomerModel(id,name,addy,postal,phone, div, null, 0,null));

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
