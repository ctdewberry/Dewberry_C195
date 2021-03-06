package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Report a results.
 * Stage for Report a results. Stage A is embedded in the primary reports stage
 * See Reports.Java for methods used in all 3 report types
 */
public class ReportAResults implements Initializable {


    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Parent scene;

    @FXML
    private Text reportAMonthChoice;

    @FXML
    private Text reportATotalAppointments;

    @FXML
    private Text reportATypeChoice;

    @FXML
    private ChoiceBox reportTypeBox;


    /**
     * Initialize.
     */
    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
