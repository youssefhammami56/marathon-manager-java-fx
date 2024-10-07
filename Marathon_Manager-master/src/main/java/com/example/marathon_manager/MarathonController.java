package com.example.marathon_manager;

import Model.Db_Connect;
import Model.Marathon;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarathonController implements Initializable {
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
    private Button dashboard_btn;

    @FXML
    private Button sponsor_btn;

    @FXML
    private Button insert_btn;

    @FXML
    private Button test_btn;

    @FXML
    private Label lbl_marathon;

    @FXML
    private Button addnewBtn;

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
    private TableColumn<Marathon, LocalDate> dateColumn;
    @FXML
    private TableColumn<Marathon, String> startLocationColumn;
    @FXML
    private TableColumn<Marathon, String> finishLocationColumn;
    @FXML
    private TableColumn<Marathon, Double> distanceColumn;

    @FXML
    private TableColumn<Marathon, String> WinnerColumn;

    @FXML
    private TextField filterTable;


    public void initialize_Marathon() {
        // Set up table columns

        marathonIdColumn.setCellValueFactory(new PropertyValueFactory<>("marathonId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startLocationColumn.setCellValueFactory(new PropertyValueFactory<>("startLocation"));
        finishLocationColumn.setCellValueFactory(new PropertyValueFactory<>("finishLocation"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        WinnerColumn.setCellValueFactory(new PropertyValueFactory<>("winner"));
        // Set up data in the table
        marathonTable.setItems(getMarathons());
    }





    public  ObservableList<Marathon> getMarathons() {

        ObservableList<Marathon> marathons = FXCollections.observableArrayList();

        marathons.clear();
        try (
                Connection con = Db_Connect.Connect_Db();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM marathon");
                ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                int marathonId = resultSet.getInt("marathon_id");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String startLocation = resultSet.getString("start_location");
                String finishLocation = resultSet.getString("finish_location");
                Double distance = resultSet.getDouble("distance");
                String winner = resultSet.getString("Winner");
                Marathon marathon = new Marathon(marathonId, name, date, startLocation, finishLocation, distance, winner);
                marathons.add(marathon);


            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load marathons from the database.");
        }

        return marathons;
    }

    public  ObservableList<Marathon> getSearchMarathons() {
        String search = filterTable.getText();
        ObservableList<Marathon> marathons = FXCollections.observableArrayList();

        marathons.clear();
        try (
                Connection con = Db_Connect.Connect_Db();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM marathon where name like ? or start_location like ? or finish_location like ? or distance like ? or Winner like ?");

        ) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            stmt.setString(3, "%" + search + "%");
            stmt.setString(4, "%" + search + "%");
            stmt.setString(5, "%" + search + "%");



            ResultSet resultSet = stmt.executeQuery();
            marathonTable.getItems().clear();

            while (resultSet.next()) {
                int marathonId = resultSet.getInt("marathon_id");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String startLocation = resultSet.getString("start_location");
                String finishLocation = resultSet.getString("finish_location");
                Double distance = resultSet.getDouble("distance");
                String winner = resultSet.getString("Winner");
                Marathon marathon = new Marathon(marathonId, name, date, startLocation, finishLocation, distance, winner);
                marathons.add(marathon);
                initialize_Marathon();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load marathons from the database.");
        }

        return marathons;
    }



    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        if(event.getSource() == marathon_btn){
            lbl_status.setText("Marathon");
            lbl_status_mini.setText("Marathon");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Marathon-view.fxml");
            Stage stage = (Stage) marathon_btn.getScene().getWindow();
            // do what you have to do
            stage.close();
        }else if(event.getSource() == runner_btn){
            System.out.println("Runner");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Runner-view.fxml");
           // Stage stage = (Stage) runner_btn.getScene().getWindow();
            // do what you have to do
           // stage.close();
        }else if(event.getSource() == sponsor_btn){
            lbl_status.setText("Sponsor");
            lbl_status_mini.setText("Sponsor");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Sponsor-view.fxml");
            Stage stage = (Stage) sponsor_btn.getScene().getWindow();

            stage.close();
        }else if(event.getSource() == participation_btn) {
            lbl_status.setText("Participation");
            lbl_status_mini.setText("Participation");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Participation-view.fxml");
            Stage stage = (Stage) participation_btn.getScene().getWindow();
            // do what you have to do
            stage.close();
        }else if(event.getSource() == chrono_btn) {
            lbl_status.setText("Chrono");
            lbl_status_mini.setText("Chrono");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Chrono-view.fxml");
            Stage stage = (Stage) chrono_btn.getScene().getWindow();

            stage.close();
        }else if(event.getSource() == dashboard_btn) {
            lbl_status.setText("Dashboard");
            lbl_status_mini.setText("Dashboard");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Dashboard.fxml");
            Stage stage = (Stage) dashboard_btn.getScene().getWindow();
            // do what you have to do
            stage.close();
        }

    }
    @FXML
    private void open_test() {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("Login-view.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Hello");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }


    @FXML
    private void insert_marathon() {
        if(areFieldsEmpty()){
        LocalDate selectedDate = datePicker.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
        String dateString = sqlDate.toString();
        //int marathonId = Integer.parseInt(marathon_id_text.getText());
        String name = name_text.getText();
        LocalDate date = datePicker.getValue();
        String startLocation = start_location_text.getText();
        String finishLocation = finish_location_text.getText();
        double distance = Double.parseDouble(distance_text.getText());

        // Insert values into the database
        try {
            Connection conn = Db_Connect.Connect_Db();
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO marathon (name, date, start_location, finish_location, distance) " +
                                "VALUES (?, ?, ?, ?, ?)");
                //statement.setInt(1, marathonId);
                statement.setString(1, name);
                statement.setDate(2, Date.valueOf(date));
                statement.setString(3, startLocation);
                statement.setString(4, finishLocation);
                statement.setDouble(5, distance);
                statement.executeUpdate();
                clearFields();
                initialize_Marathon();
                //clearForm();
                //  initialize_Marathon(); // Refresh the table view
            }} catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert the marathon into the database.");
            insert_btn.setVisible(false);
            addnewBtn.setVisible(true);
        }
        }else{
            showAlert("Error", "Please fill all the fields.");
        }

    }

    @FXML
    private void updateMarathon(ActionEvent actionEvent) {
        try (Connection con = Db_Connect.Connect_Db();
             PreparedStatement stmt = con.prepareStatement("UPDATE marathon SET name = ?, date = ?, start_location = ?, finish_location = ?, distance = ? WHERE marathon_id = ?")) {

            stmt.setString(1, name_text.getText());
            stmt.setDate(2, datePicker.getValue() == null ? null : Date.valueOf(datePicker.getValue()));
            stmt.setString(3, start_location_text.getText());
            stmt.setString(4,finish_location_text.getText());
            stmt.setDouble(5, distance_text.getText() == null ? null : Double.parseDouble(distance_text.getText()));
            stmt.setInt(6, marathon_id_text.getText() == null ? null : Integer.parseInt(marathon_id_text.getText()));

            stmt.executeUpdate();

            // Refresh the marathon table
            initialize_Marathon();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update marathon"+ e.getMessage());
        }
    }
    @FXML
    public void deleteMarathon(ActionEvent actionEvent) {
        try (Connection con = Db_Connect.Connect_Db();
             PreparedStatement stmt = con.prepareStatement("DELETE FROM marathon WHERE marathon_id = ?")) {

            stmt.setInt(1, marathon_id_text.getText() == null ? null : Integer.parseInt(marathon_id_text.getText()));

            stmt.executeUpdate();

            // Refresh the marathon table
            initialize_Marathon();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete marathon"+ e.getMessage());
        }
    }

    @FXML
    private void addnew_marathon() {
        lbl_marathon.setVisible(false);
        marathon_id_text.setVisible(false);
        insert_btn.setVisible(true);
        addnewBtn.setVisible(false);
        clearFields();

    }


    @FXML
    private void dashboard_close() {
        System.exit(0);
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clearFields() {
        marathon_id_text.clear();
        name_text.clear();
        start_location_text.clear();
        finish_location_text.clear();
        distance_text.clear();
        datePicker.setValue(null);
    }


    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        int  index = marathonTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {

            return;
        }
        marathon_id_text.setText(marathonIdColumn.getCellData(index).toString());
        name_text.setText(nameColumn.getCellData(index).toString());
        datePicker.setValue(dateColumn.getCellData(index));
        start_location_text.setText(startLocationColumn.getCellData(index).toString());
        finish_location_text.setText(finishLocationColumn.getCellData(index).toString());
        distance_text.setText(distanceColumn.getCellData(index).toString());

    }


    private void showInterface(String name) throws Exception {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(name));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
            System.out.println(name + " is opened   ");
        } catch (IOException e) {
            System.out.println("Error");
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize_Marathon();
    }

    @FXML
    public void search() {
        String searchQuery = "SELECT * FROM marathon WHERE name LIKE '%" + filterTable.getText() + "%'";
        try {
            Connection con = Db_Connect.Connect_Db();
            PreparedStatement statement = con.prepareStatement(searchQuery);
            ResultSet resultSet = statement.executeQuery();
            marathonTable.getItems().clear();
            while (resultSet.next()) {
                int marathonId = resultSet.getInt("marathon_id");
                String name = resultSet.getString("name");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String startLocation = resultSet.getString("start_location");
                String finishLocation = resultSet.getString("finish_location");
                Double distance = resultSet.getDouble("distance");
                String winner = resultSet.getString("Winner");
                Marathon marathon = new Marathon(marathonId, name, date, startLocation, finishLocation, distance, winner);
                marathonTable.getItems().add(marathon);
                initialize_Marathon();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean areFieldsEmpty() {
        System.out.println("areFieldsEmpty");
        LocalDate selectedDate = datePicker.getValue();
        String name = name_text.getText();
        String startLocation = start_location_text.getText();
        String finishLocation = finish_location_text.getText();
        String distanceStr = distance_text.getText();

        if( selectedDate == null || name.isEmpty() || startLocation.isEmpty() || finishLocation.isEmpty() || distanceStr.isEmpty()){
            return false;
        }
        return true;
    }
}
