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


/**CustomersScreen. Sets the scene for the primary Customers Screen
 */
public class CustomersScreen implements Initializable {

    /**The Stage.
     */
    Stage stage;
    /**The Scene.
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




    /** On action back. Takes user back to main page
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Menu");
        stage.show();
    }



    /** On action add customer. Opens window for user to add a new customer
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Customer");
        stage.show();
    }


    /** On action modify customer. Opens window for user to modify customer info
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {

        try {
            //Attempts to get information about selected customer to send to modify custoemr screen

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
            // catches exception if no customer selected when user clicks modify
            return;
        }
    }


    /** Initialize. Initialize
     * */
    @FXML
    void initialize() {

    }

    /** Initialize. Populate customer table on load.
     * Lambda used below to Refresh Selected Customer info. Below the customer table view there is some text that updates
     * with the selected customers next appointment. Though not required, in the real world this would be a great
     * feature for the user as they would be able to quickly see the customers next appointment without having to
     * switch to the appointment screen or report screen. I felt this use was justified as I can see a feature like this
     * being used in the real world.
     * */
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
        /** Refresh Selected Customer. Lambda to update selected customers next appointment information
         * This updates the appointment information below the customerTableView
         * This allows the user a quick glance into a customers future schedule without having to run a report or switch to the appointments screen
         */
        customerTableView.setOnMouseClicked(e -> refreshSelectedCustomer());
    }

    /** Refresh Implementation. Method to detect the selected customer and query their next appointment.
     * This is the interface of the lambda used above*/

    private void refreshSelectedCustomer() {
        try {
            int currentCustomer = customerTableView.getSelectionModel().getSelectedItem().getCustomerID();
            nextAppointment.setText(CustomerQuery.getNextAppointment(currentCustomer));
        } catch (Exception e) {
            //Catches exception if no customers are selected
        }
    }

    /**onActionDeleteCustomer.Deletes selected customer
     * Prompts user before delete.
     * Deletes customers appointments before deleting customer
     * @param event the event
     * @throws IOException the io exception
     */
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
            //Exception caught if no customer selected when user clicks delete

            Alert noCustomerSelectedForDeletion = new Alert(Alert.AlertType.INFORMATION);
            noCustomerSelectedForDeletion.setTitle("Delete customer");
            noCustomerSelectedForDeletion.setHeaderText("Delete customer");
            noCustomerSelectedForDeletion.setContentText("Please select a customer for deletion.");
            Optional<ButtonType> result2 = noCustomerSelectedForDeletion.showAndWait();
        }
    }
}

