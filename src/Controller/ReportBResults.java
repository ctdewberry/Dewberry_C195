package Controller;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportBResults implements Initializable {

    ObservableList<String> reportTypeList = FXCollections.observableArrayList("Appointments by type/month","Appointments by contact","Appointments by user/contact");

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private ComboBox comboBoxContact = new ComboBox();

    @FXML
    private Button runReportB;

    @FXML
    private TableView<AppointmentModel> reportContactTableView;

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

//    @FXML
//    void onActionBack(ActionEvent event) throws IOException {
//
//        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//        scene = FXMLLoader.load(getClass().getResource("/View/MainPage.fxml"));
//        stage.setScene(new Scene(scene));
//        stage.setTitle("Main Menu");
//
//        stage.show();
//    }

    @FXML
    private ChoiceBox reportTypeBox;

//    @FXML
//    void onActionReportOptions(ActionEvent event) throws IOException {
////        String selectedReportType = (String) reportTypeBox.getValue();
////        System.out.println(selectedReportType);
////        System.out.println(reportTypeList.indexOf(reportTypeBox.getValue()));
//
//        int selectedReportIndex = reportTypeList.indexOf(reportTypeBox.getValue());
//        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
//
//        switch (selectedReportIndex) {
//            case 0:
//                scene = FXMLLoader.load(getClass().getResource("/view/ReportA.fxml"));
//                break;
//            case 1:
//                scene = FXMLLoader.load(getClass().getResource("/view/ReportB.fxml"));
//                break;
//            case 2:
//                scene = FXMLLoader.load(getClass().getResource("/view/ReportC.fxml"));
//                break;
//        }
//
//        stage.setScene(new Scene(scene));
//        stage.setTitle("Reports");
//
//        stage.show();
//
//
//
//    }


    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportTypeBox.setValue("Appointments by type/month");
        reportTypeBox.setItems(reportTypeList);
    }
}
