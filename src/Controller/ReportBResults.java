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

/**Report B results. Initialize stage for Report B results.
 * */
public class ReportBResults implements Initializable {


    /**The Stage.
     */
    Stage stage;
    /**The Scene.
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


    @FXML
    private ChoiceBox reportTypeBox;


    /** Initialize. Initialized Report B Results Screen
     */
    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
