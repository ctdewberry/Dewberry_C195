package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

//    private Node TestPane1 = new ;
//    private Node TestPane1;


    @FXML
    private BorderPane mainPain;

    @FXML
    private MenuItem view_a;

    @FXML
    private MenuItem view_b;




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
//        String selectedReportType = (String) reportTypeBox.getValue();
//        System.out.println(selectedReportType);
//        System.out.println(reportTypeList.indexOf(reportTypeBox.getValue()));


        //tester
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
//        stage.show();

//        try {
//            System.out.println("testThis");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TestPane2.fxml"));
//            loader.setController(this);
//
//            mainPain.setCenter(loader.load());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//                String selectedReportType = (String) reportTypeBox.getValue();
                int selectedReportIndex = reportTypeList.indexOf(reportTypeBox.getValue());
//                reportTypeBox.getValue();
                switch (selectedReportIndex) {
            case 0:
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportA.fxml"));
                    loader.setController(this);
                    mainPain.setCenter(loader.load());
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            case 1:
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportB.fxml"));
                    loader.setController(this);
                    mainPain.setCenter(loader.load());
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            case 2:
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReportC.fxml"));
                    loader.setController(this);
                    mainPain.setCenter(loader.load());
                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
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


//        reportTypeBox.setOnAction(e -> {
//            try {
//                testClick();
//                System.out.println("gotThisFar");
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        });

//        testPane.setCenter(TestPane1);

//        try {
////            System.out.println(reportTypeBox.getValue());
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("TestPane1.fxml"));
//            loader.setController(this);
//            mainPain.setCenter(loader.load());
//        } catch (Exception e) {
//        }

    }


//    @FXML
//    private void testClick(ActionEvent event) throws IOException {
//        System.out.println("test1");
//        //tester
////        System.out.println(reportTypeList.indexOf(reportTypeBox));
//        try {
//            System.out.println("test2");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TestPane1.fxml"));
//            loader.setController(this);
//            mainPain.setCenter(loader.load());
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//    }

//    @FXML
//    private void testClick2(ActionEvent event) throws IOException {
//        System.out.println("tes3");
//        //tester
////        System.out.println(reportTypeList.indexOf(reportTypeBox));
//        try {
////            System.out.println(reportTypeBox.getValue());
//            System.out.println("test4");
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TestPane2.fxml"));
//            loader.setController(this);
//            mainPain.setCenter(loader.load());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}