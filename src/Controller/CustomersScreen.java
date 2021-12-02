package Controller;

import DAO.CustomerQuery;
import Model.CustomerModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class CustomersScreen implements Initializable {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;


    @FXML
    private TableView<CustomerModel> customerTableView;

    @FXML
    private TableColumn<CustomerModel, Integer> custIDCol;

    @FXML
    private TableColumn<CustomerModel, String> custNameCol;

    @FXML
    private TableColumn<CustomerModel, String> custAddressCol;

    @FXML
    private TableColumn<CustomerModel, String> custCodeCol;

    @FXML
    private TableColumn<CustomerModel, String> custPhoneCol;

    @FXML
    private TableColumn<CustomerModel, Integer> custDivisionIDCol;

    @FXML
    private TableColumn<CustomerModel, String> custDivisionCol;

    @FXML
    private TableColumn<CustomerModel, Integer> custCountryIDCol;

    @FXML
    private TableColumn<CustomerModel, String> custCountryCol;


    @FXML
    private Text nextAppointment;


    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Menu");
        stage.show();
    }

    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Customer");
        stage.show();
    }


    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {

        try {

        int currentCustomer = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CustomerModify.fxml"));
        loader.load();
        CustomerModify CustomerModify = loader.getController();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        CustomerModify.sendCustomer(CustomerQuery.getCurrentCustomer(currentCustomer));
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.setTitle("Modify Customer");
        stage.show();
                } catch (NullPointerException e) {
            return;
        }
    }


    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        customerTableView.setItems(DAO.CustomerQuery.getAllCustomers());
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        custDivisionIDCol.setCellValueFactory(new PropertyValueFactory<>("customerDivisionID"));
        custDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
        custCountryIDCol.setCellValueFactory(new PropertyValueFactory<>("customerCountryID"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));
        customerTableView.getSortOrder().add(custIDCol);
        //lambda to refresh selected customer (provides a quick look into the customers next appointment)
        customerTableView.setOnMouseClicked(e -> refreshSelectedCustomer());

    }

    private void refreshSelectedCustomer() {
        try {
            int currentCustomer = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();
            nextAppointment.setText(CustomerQuery.getNextAppointment(currentCustomer));
        } catch (Exception e) {
            //exception, no customers selected
        }
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {
        try {
            int currentCustomer = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();
            String currentName = customerTableView.getSelectionModel().getSelectedItem().getCustomerName();
            Alert alertConfirmCustomerDelete = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmCustomerDelete.setTitle("Delete customer");
            alertConfirmCustomerDelete.setHeaderText("Delete customer");
            alertConfirmCustomerDelete.setContentText("Do you want to delete customer: " + currentName + "? \nThis will delete all of this customers appointments.");
            Optional<ButtonType> result = alertConfirmCustomerDelete.showAndWait();
            if (result.get() == ButtonType.OK) {
                CustomerQuery.deleteCustomer(currentCustomer);
                customerTableView.setItems(DAO.CustomerQuery.getAllCustomers());

                Alert alertConfirmCustomerIsDeleted = new Alert(Alert.AlertType.INFORMATION);
                alertConfirmCustomerIsDeleted.setTitle("Delete customer");
                alertConfirmCustomerIsDeleted.setHeaderText("Delete customer");
                alertConfirmCustomerIsDeleted.setContentText(currentName + " has been deleted.");
                Optional<ButtonType> result2 = alertConfirmCustomerIsDeleted.showAndWait();
                customerTableView.getSortOrder().add(custIDCol);

            }
        } catch (Exception e) {
            Alert noCustomerSelectedForDeletion = new Alert(Alert.AlertType.INFORMATION);
            noCustomerSelectedForDeletion.setTitle("Delete customer");
            noCustomerSelectedForDeletion.setHeaderText("Delete customer");
            noCustomerSelectedForDeletion.setContentText("Please select a customer for deletion.");
            Optional<ButtonType> result2 = noCustomerSelectedForDeletion.showAndWait();
        }
    }
}

