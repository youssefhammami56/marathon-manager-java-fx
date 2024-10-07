package com.example.marathon_manager;

import Model.Db_Connect;
import Model.Marathon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController  implements Initializable {
    @FXML
    private Button chrono_btn;



    @FXML
    private Label lbl_status;

    @FXML
    private Label lbl_status_mini;

    @FXML
    private Button marathon_btn;

    @FXML
    private Button participation_btn;

    @FXML
    private Pane pnl_status;

    @FXML
    private Button runner_btn;

    @FXML
    private Button sponsor_btn;

    @FXML
    private Button insert_btn;

    @FXML
    private Button rankingBtn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button close;

    @FXML
    private TextField marathon_id_text;
    @FXML
    private TextField name_text;

    @FXML
    private TextField start_location_text;

    @FXML
    private TextField finish_location_text;

    @FXML
    private TextField distance_text;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Marathon> marathonTable;
    @FXML
    private TableColumn<Marathon, Integer> marathonIdColumn;
    @FXML
    private TableColumn<Marathon, String> nameColumn;
    @FXML
    private TableColumn<Marathon, Date> dateColumn;
    @FXML
    private TableColumn<Marathon, String> startLocationColumn;
    @FXML
    private TableColumn<Marathon, String> finishLocationColumn;
    @FXML
    private TableColumn<Marathon, String> distanceColumn;


    @FXML
    private Label totalEnrolledTxt;

    @FXML
    private Label totalMarathonTxt;

    @FXML
    private Label totalSponsorTxt;

    @FXML
    private BarChart<?, ?> totalenrolledchart;

    @FXML
    private LineChart<?, ?> totalmarathonchart;

    @FXML
    private AreaChart<?, ?> totalsponsorchart;

    @FXML
    void CloseAction(ActionEvent event) {

    }




    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource() == marathon_btn){
           showInterface("Marathon-view.fxml");
           Stage stage = (Stage) marathon_btn.getScene().getWindow();
              stage.close();
        }else if(event.getSource() == runner_btn){
           showInterface("Runner-view.fxml");
           Stage stage = (Stage) runner_btn.getScene().getWindow();
            //  stage.close();
        }else if(event.getSource() == sponsor_btn){
              showInterface("Sponsor-view.fxml");
              Stage stage = (Stage) sponsor_btn.getScene().getWindow();
                stage.close();

        }else if(event.getSource() == participation_btn) {
          showInterface("Participation-view.fxml");
          Stage stage = (Stage) participation_btn.getScene().getWindow();
              stage.close();
        }else if(event.getSource() == chrono_btn) {
            showInterface("Chrono-view.fxml");
            Stage stage = (Stage) chrono_btn.getScene().getWindow();
              stage.close();
        }else if(event.getSource() == dashboard_btn) {
             showInterface("Dashboard.fxml");
             Stage stage = (Stage) dashboard_btn.getScene().getWindow();
              stage.close();
        }else if(event.getSource() == rankingBtn) {
            showInterface("Ranking-view.fxml");
            Stage stage = (Stage) rankingBtn.getScene().getWindow();
              stage.close();}

    }
    @FXML
    private void open_test() {
    }


    private void showInterface(String name) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(name));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(name);
            stage.setScene(scene);
            stage.show();
            System.out.println(name + " is opened   ");
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    @FXML
    private void dashboard_close() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void homeDisplayTotalEnrolledRunners() {
        Connection con = Db_Connect.Connect_Db();


        int countEnrolled = 0;

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) as number FROM runner ");
                ResultSet result = stmt.executeQuery();
            if (result.next()) {
                countEnrolled = result.getInt("number");
            }

            totalEnrolledTxt.setText(String.valueOf(countEnrolled));
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void homeDisplayTotalMarathons() {
        Connection con = Db_Connect.Connect_Db();


        int countEnrolled = 0;

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM marathon ");
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                countEnrolled = result.getInt("COUNT(*)");
            }

            totalMarathonTxt.setText(String.valueOf(countEnrolled));
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }
    public void homeDisplayTotalSponsors() {
        Connection con = Db_Connect.Connect_Db();


        int countEnrolled = 0;

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM sponsor ");
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                countEnrolled = result.getInt("COUNT(*)");
            }

            totalSponsorTxt.setText(String.valueOf(countEnrolled));
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    public void homeDisplayTotalEnrolledChart() {
        Connection con = Db_Connect.Connect_Db();
        totalenrolledchart.getData().clear();

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT age, COUNT(*) FROM runner  GROUP BY age ORDER BY TIMESTAMP(age) ASC LIMIT 5");
                    ResultSet result = stmt.executeQuery();
            XYChart.Series chart = new XYChart.Series();
            while(result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            totalenrolledchart.getData().add(chart);
            System.out.println("Chart is displayed");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
    public void homeDisplayMarathonChart() {
        Connection con = Db_Connect.Connect_Db();
        totalmarathonchart.getData().clear();

        try {
            PreparedStatement stmt = con.prepareStatement("SELECT date, COUNT(*) FROM marathon  GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5");
            ResultSet result = stmt.executeQuery();
            XYChart.Series chart = new XYChart.Series();

            while(result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            totalmarathonchart.getData().add(chart);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void homeDisplaySponsorChart() {
        Connection con = Db_Connect.Connect_Db();
        totalsponsorchart.getData().clear();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT sponsorship_amount, COUNT(*) FROM sponsor  GROUP BY sponsorship_amount ORDER BY sponsorship_amount ASC ");
            ResultSet result = stmt.executeQuery();
            XYChart.Series chart = new XYChart.Series();
            while(result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            totalsponsorchart.getData().add(chart);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeDisplayTotalEnrolledRunners();
        homeDisplayTotalMarathons();
        homeDisplayTotalSponsors();
        homeDisplayTotalEnrolledChart();
        homeDisplayMarathonChart();
        homeDisplaySponsorChart();
       if(LoginviewController.role.equals("user")){
        marathon_btn.setVisible(false);
        participation_btn.setVisible(false);
        sponsor_btn.setVisible(false);
        chrono_btn.setVisible(false);
        rankingBtn.setVisible(false);
       }else if(LoginviewController.role.equals("chrono")) {
           marathon_btn.setVisible(false);
           participation_btn.setVisible(false);
           sponsor_btn.setVisible(false);
           chrono_btn.setVisible(false);
           rankingBtn.setVisible(true);
           runner_btn.setVisible(false);

       }
    }
    //ok
}
