package Controller;

import DAO.ReportsQuery;
import Model.AppointmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class Reports implements Initializable {

    ObservableList<String> reportTypeList = FXCollections.observableArrayList("Appointments by type/month", "Appointments by contact", "Appointments by location");

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private ReportA ReportA;

    @FXML
    private BorderPane mainPain;

    @FXML
    private MenuItem view_a;

    @FXML
    private MenuItem view_b;

    //Report A Stuff

    @FXML
    private ComboBox comboBoxMonth = new ComboBox();

    @FXML
    private ComboBox comboBoxType = new ComboBox();

    @FXML
    private Text reportAMonthChoice;

    @FXML
    private Text reportATotalAppointments;

    @FXML
    private Text reportATypeChoice;

    @FXML
    private Button runReportA;


    //Report B Stuff

    @FXML
    private ComboBox comboBoxContact = new ComboBox();

    @FXML
    private Button runReportB;



    @FXML
    private TableView<AppointmentModel> reportContactTableView;

    @FXML
    private TableView<AppointmentModel> reportLocationTableView;

    @FXML
    private TableColumn<AppointmentModel, Integer> apptIDCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptTitleCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptDescCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptLocCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptTypeCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptStartDateTimeCol;

    @FXML
    private TableColumn<AppointmentModel, String> apptEndDateTimeCol;

    @FXML
    private TableColumn<AppointmentModel, Integer> apptCustIDCol;

    @FXML
    private TableColumn<AppointmentModel, Integer> apptUserIDCol;

    @FXML
    private Text reportBContact;

//Report C Stuff
@FXML
    private TableColumn<AppointmentModel, Integer> apptContactIDCol;

    @FXML
    private ComboBox comboBoxLocation = new ComboBox();


    @FXML
    private Text reportCLocation;





    //Report C Stuff
//    @FXML
//    private ComboBox comboBoxContact = new ComboBox();
//
//    @FXML
//    private ComboBox comboBoxUser = new ComboBox();

    @FXML
    private Button runReportC;



    //Report Main Stuff

    @FXML
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Page");
        stage.show();
    }

    @FXML
    private ChoiceBox reportTypeBox;


    @FXML
    void onActionReportOptions(ActionEvent event) throws IOException {
                int selectedReportIndex = reportTypeList.indexOf(reportTypeBox.getValue());
                switch (selectedReportIndex) {
            case 0:
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportA.fxml"));
                    loader.setController(this);
                    mainPain.setCenter(loader.load());
//                    System.out.println(ReportsQuery.ReportMonthChoices());
//                    System.out.println(ReportsQuery.ReportTypeChoices());
                } catch (Exception e) {
                    e.printStackTrace();

                }
                reportTypeBox.getSelectionModel().select(0);

                break;
            case 1:
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportB.fxml"));
                    loader.setController(this);
                    mainPain.setCenter(loader.load());
                } catch (Exception e) {
                    e.printStackTrace();

                }
                reportTypeBox.getSelectionModel().select(1);

                break;
            case 2:
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportC.fxml"));
                    loader.setController(this);
                    mainPain.setCenter(loader.load());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                reportTypeBox.getSelectionModel().select(2);
                break;
        }
    }




    //Report A Action
    @FXML
    void onActionRunReportA(ActionEvent event) {

        String chosenMonthYear = comboBoxMonth.getSelectionModel().getSelectedItem().toString();
        String selectedMonthString = YearMonth.parse(chosenMonthYear,DateTimeFormatter.ofPattern("MM-yyyy")).getMonth().name().toString();
        String selectedYearString = String.valueOf(YearMonth.parse(chosenMonthYear,DateTimeFormatter.ofPattern("MM-yyyy")).getYear());
        Integer selectedMonth = YearMonth.parse(chosenMonthYear,DateTimeFormatter.ofPattern("MM-yyyy")).getMonthValue();
        Integer selectedYear = YearMonth.parse(chosenMonthYear,DateTimeFormatter.ofPattern("MM-yyyy")).getYear();

        String chosenType = comboBoxType.getSelectionModel().getSelectedItem().toString();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportAResults.fxml"));
            loader.setController(this);
            mainPain.setCenter(loader.load());
            reportAMonthChoice.setText(selectedMonthString + " " +selectedYearString);
            reportATypeChoice.setText(chosenType);
            reportATotalAppointments.setText(String.valueOf(ReportsQuery.ReportATotalsQuery(selectedMonth,selectedYear,chosenType)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    void onActionRunReportB(ActionEvent event) {
        try {
            System.out.println("here");
            String chosenContact = comboBoxContact.getSelectionModel().getSelectedItem().toString();
            System.out.println(chosenContact);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportBResults.fxml"));
            loader.setController(this);
            mainPain.setCenter(loader.load());




            reportBContact.setText(chosenContact);
            reportContactTableView.setItems(DAO.AppointmentQuery.getAppointmentsByContact(chosenContact));
            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            apptEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            reportContactTableView.getSortOrder().add(apptIDCol);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionRunReportC(ActionEvent event) {
        try {
            System.out.println("here");
            String chosenLocation = comboBoxLocation.getSelectionModel().getSelectedItem().toString();
            System.out.println(chosenLocation);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportCResults.fxml"));
            loader.setController(this);
            mainPain.setCenter(loader.load());




            reportCLocation.setText(chosenLocation);
            reportLocationTableView.setItems(DAO.AppointmentQuery.getAppointmentsByLocation(chosenLocation));
            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            apptEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            apptCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            apptContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            reportLocationTableView.getSortOrder().add(apptIDCol);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportTypeBox.getItems().setAll(reportTypeList);
        reportTypeBox.getSelectionModel().selectFirst();
        comboBoxMonth.getItems().setAll(ReportsQuery.ReportMonthChoices());
        comboBoxMonth.getSelectionModel().selectFirst();
        comboBoxType.getItems().setAll(ReportsQuery.ReportTypeChoices());
        comboBoxType.getSelectionModel().selectFirst();
        comboBoxContact.getItems().setAll(ReportsQuery.ReportContactChoices());
        comboBoxContact.getSelectionModel().selectFirst();
        comboBoxLocation.getItems().setAll(ReportsQuery.ReportLocationChoices());
        comboBoxLocation.getSelectionModel().selectFirst();

    }


}