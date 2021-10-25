package Controller;

import DAO.CustomerQuery;
import Model.CustomerList;
import Model.CustomerModel;
import com.sun.javafx.UnmodifiableArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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
    private Text customerAddress;

    @FXML
    private Text customerID;

    @FXML
    private Text customerName;

    @FXML
    private Text customerPhone;

    @FXML
    private Text customerPostal;

    @FXML
    private Text nextAppointment;



    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerAdd.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerModify.fxml"));
        stage.setScene(new Scene(scene));
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
        customerTableView.setOnMouseClicked(e -> updateSelectedCustomer());

    }

    private void updateSelectedCustomer() {
        int currentCustomer = customerTableView.getSelectionModel().getSelectedIndex();
        ObservableList<CustomerModel> selectedCustomer = DAO.CustomerQuery.getAllCustomers();
        System.out.println(selectedCustomer.get(currentCustomer).getCustomerName());
        customerID.setText(String.valueOf(selectedCustomer.get(currentCustomer).getCustomerID()));
        customerName.setText(selectedCustomer.get(currentCustomer).getCustomerName());
        customerAddress.setText(selectedCustomer.get(currentCustomer).getCustomerAddress());
        customerPostal.setText(selectedCustomer.get(currentCustomer).getCustomerCode());
        customerPhone.setText(selectedCustomer.get(currentCustomer).getCustomerPhone());
        nextAppointment.setText(CustomerQuery.getNextAppointment(selectedCustomer.get(currentCustomer).getCustomerID()));

//        selectedCustomer.get(currentCustomer).getCustomerID()
        //select appointments.Start, appointments.Customer_ID from appointments WHERE Customer_ID=1
    }

}

