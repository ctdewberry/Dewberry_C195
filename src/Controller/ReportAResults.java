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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportAResults implements Initializable {

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
    void onActionBack(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private ChoiceBox reportTypeBox;

    @FXML
    void onActionReportOptions(ActionEvent event) throws IOException {
//        String selectedReportType = (String) reportTypeBox.getValue();
//        System.out.println(selectedReportType);
//        System.out.println(reportTypeList.indexOf(reportTypeBox.getValue()));

        int selectedReportIndex = reportTypeList.indexOf(reportTypeBox.getValue());
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        switch (selectedReportIndex) {
            case 0:
                scene = FXMLLoader.load(getClass().getResource("/view/ReportA.fxml"));
                break;
            case 1:
                scene = FXMLLoader.load(getClass().getResource("/view/ReportB.fxml"));
                break;
            case 2:
                scene = FXMLLoader.load(getClass().getResource("/view/ReportC.fxml"));
                break;
        }

        stage.setScene(new Scene(scene));
        stage.show();



    }


    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportTypeBox.setValue("Appointments by type/month");
        reportTypeBox.setItems(reportTypeList);
    }
}
