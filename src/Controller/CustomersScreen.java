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
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.net.URL;
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
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerModify.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Modify Customer");
        stage.show();
    }

    @FXML
    void onActionViewSchedule(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerSchedule.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("View Schedule");
        stage.show();
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
        customerTableView.setOnMouseClicked(e -> refreshSelectedCustomer());

    }

    private void refreshSelectedCustomer() {
        try {
            int currentCustomer = customerTableView.getSelectionModel().getSelectedIndex();
            ObservableList<CustomerModel> selectedCustomer = DAO.CustomerQuery.getAllCustomers();
            nextAppointment.setText(CustomerQuery.getNextAppointment(selectedCustomer.get(currentCustomer).getCustomerID()));
        }
        catch (Exception e) {
            System.out.println("No selected customers");
        }

    }

}

