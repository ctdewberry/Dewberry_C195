package Controller;

import DAO.ReportsQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    ObservableList<String> reportTypeList = FXCollections.observableArrayList("Appointments by type/month", "Appointments by contact", "Appointments by user/contact");

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
//    private ChoiceBox choiceBoxMonth;
    private ChoiceBox choiceBoxMonth = new ChoiceBox();



    @FXML
    private ChoiceBox choiceBoxType = new ChoiceBox();

    @FXML
    private Text reportAMonthChoice;

    @FXML
    private Text reportATotalAppointments;

    @FXML
    private Text reportATypeChoice;

    @FXML
    private Button runReportA;


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

    @FXML
    void onActionRunReportA(ActionEvent event) {
        String chosenMonthYear = choiceBoxMonth.getSelectionModel().getSelectedItem().toString();
        String selectedMonthString = YearMonth.parse(chosenMonthYear,DateTimeFormatter.ofPattern("MM-yyyy")).getMonth().name().toString();
        String selectedYearString = String.valueOf(YearMonth.parse(chosenMonthYear,DateTimeFormatter.ofPattern("MM-yyyy")).getYear());
        Month selectedMonth = YearMonth.parse(chosenMonthYear,DateTimeFormatter.ofPattern("MM-yyyy")).getMonth();
        Integer selectedYear = YearMonth.parse(chosenMonthYear,DateTimeFormatter.ofPattern("MM-yyyy")).getYear();

        String chosenType = choiceBoxType.getSelectionModel().getSelectedItem().toString();
//        System.out.println(selectedMonthString + " " +selectedYearString);



//working
//        System.out.println(choiceBoxMonth.getSelectionModel().getSelectedItem());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportAResults.fxml"));
            loader.setController(this);
            mainPain.setCenter(loader.load());
            reportAMonthChoice.setText(selectedMonthString + " " +selectedYearString);
            reportATypeChoice.setText(chosenType);
            System.out.println(selectedMonth);
            System.out.println(selectedYear);
            System.out.println(chosenType);
//            reportATotalAppointments.setText(String.valueOf(ReportsQuery.ReportATotalsQuery(selectedMonth,selectedYear,chosenType)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        reportTypeBox.setValue("Appointments by type/month");
//        reportTypeBox.setItems(reportTypeList);
        reportTypeBox.getItems().setAll(reportTypeList);
        reportTypeBox.getSelectionModel().selectFirst();
        choiceBoxMonth.getItems().setAll(ReportsQuery.ReportMonthChoices());
        choiceBoxMonth.getSelectionModel().selectFirst();
        choiceBoxType.getItems().setAll(ReportsQuery.ReportTypeChoices());
        choiceBoxType.getSelectionModel().selectFirst();

    }


}