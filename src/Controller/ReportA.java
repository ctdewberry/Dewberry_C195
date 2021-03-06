package Controller;

import DAO.ReportsQuery;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**Report A. Initializes Report A subscene within the Reports scene
 */
public class ReportA implements Initializable {

    /**The Stage.
     */
    Stage stage;
    /**The Scene.
     */
    Parent scene;

    @FXML
    private ChoiceBox choiceBoxMonth;

    @FXML
    private ChoiceBox choiceBoxType;


    /**Initialize. Initialize Report A Screen
     */
    @FXML
    void initialize() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
