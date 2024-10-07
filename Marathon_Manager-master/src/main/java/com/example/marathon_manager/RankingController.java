package com.example.marathon_manager;

import Model.Db_Connect;
import Model.Ranking;
import Model.Runner;
import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.sql.*;

public class RankingController {

    @FXML
    private Button DashboardBtn;

    @FXML
    private Button insert_btn;

    @FXML
    private Label lbl_status;

    @FXML
    private Label lbl_status_mini;

    @FXML
    private ComboBox<String> marathComboBox;

    @FXML
    private TableColumn<?, ?> marathonIdColumn;

    @FXML
    private Pane pnl_status;

    public static int Ma_Id = 0;
    @FXML
    private TableView<Ranking> rankTable;

    @FXML
    private TableColumn<?, ?> runColumn;

    @FXML
    private TableColumn<?, ?> runneridcolumn;

    @FXML
    private TextField runtimeTxt;

    @FXML
    private TextField winnertxt;

    public void handleButtonAction(ActionEvent actionEvent) {

    }

    public void getSelected(MouseEvent mouseEvent) {

    }






    public void AddRankAction(ActionEvent actionEvent) {
        try (Connection con = Db_Connect.Connect_Db();
             PreparedStatement stmt = con.prepareStatement("UPDATE marathon SET  Winner = ? WHERE name = ?")) {

            stmt.setString(1, winnertxt.getText());
            stmt.setString(2, marathComboBox.getValue());

            stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update marathon"+ e.getMessage());
        }
    }
    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void initialize_Ranking(){
        marathonIdColumn.setCellValueFactory(new PropertyValueFactory<>("marathonname"));
        runneridcolumn.setCellValueFactory(new PropertyValueFactory<>("runnername"));
        runColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        rankTable.setItems(getRunnerList());

    }
    @FXML
    public void select_Combobox(MouseEvent mouseEvent) {

        try {
            System.out.println("Connection established");
            Connection con = Db_Connect.Connect_Db();

            PreparedStatement stmt = con.prepareStatement("Select name FROM marathon ");

            ObservableList data = FXCollections.observableArrayList();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                data.add(resultSet.getString("name"));
            }
            marathComboBox.setItems(data);

        } catch ( SQLException e) {
            System.out.println("Error");
        }
    }

    public  ObservableList<Ranking> getRunnerList(){

        ObservableList<Ranking> rankings = FXCollections.observableArrayList();
              try (
                Connection con = Db_Connect.Connect_Db();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM ranking where marathon_name = ? order by run_over_time asc")) {
                    stmt.setString(1, marathComboBox.getValue());
            ResultSet resultSet = stmt.executeQuery();

                  while (resultSet.next()) {
                String marathonname = resultSet.getString("marathon_name");
                String runnername = resultSet.getString("runner_name");
                String runnertime = resultSet.getString("run_over_time");
                Ranking ranking = new Ranking(marathonname, runnername, runnertime);
                System.out.println(rankings);
                rankings.add(ranking);
                System.out.println(rankings);
            }
            System.out.println(Ma_Id);
        }catch (SQLException e) {
            e.printStackTrace();

    }
        return rankings;
}

    public void getSelectedColu(javafx.scene.input.MouseEvent mouseEvent) {

        int index = rankTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        winnertxt.setText(runneridcolumn.getCellData(index).toString());
        runtimeTxt.setText(runColumn.getCellData(index).toString());


    }


}
