package com.example.marathon_manager;

import Model.Db_Connect;
import Model.Runner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RunnerController {
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
    private Label lbl_runner;

    @FXML
    private Button addnewBtn;

    @FXML
    private TextField runner_id_text;

    @FXML
    private TextField name_text;

    @FXML
    private TextField age_text;

    @FXML
    private TextField phone_text;

    @FXML
    private TextField gender_text;

    @FXML
    private TextField email_text;

    @FXML
    private TableView<Runner> runnerTable;

    @FXML
    private TableColumn<Runner, Integer> runnerIdColumn;

    @FXML
    private TableColumn<Runner, String> nameColumn;

    @FXML
    private TableColumn<Runner, Integer> ageColumn;

    @FXML
    private TableColumn<Runner, String> phoneColumn;

    @FXML
    private TableColumn<Runner, String> genderColumn;

    @FXML
    private TableColumn<Runner, String> emailColumn;

    public void initialize_Runner() {
        // Set up table columns
        runnerIdColumn.setCellValueFactory(new PropertyValueFactory<>("runnerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Set up data in the table
        runnerTable.setItems(getRunners());
    }

    public static ObservableList<Runner> getRunners() {
        ObservableList<Runner> runners = FXCollections.observableArrayList();

        runners.clear();
        try (
                Connection con = Db_Connect.Connect_Db();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM runner");
                ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                int runnerId = resultSet.getInt("runner_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String phone = resultSet.getString("phone");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                Runner runner = new Runner(runnerId, name, String.valueOf(age), phone, gender, email);
                runners.add(runner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the runner into the database.");
        }

        return runners;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        if (event.getSource() == marathon_btn) {
            lbl_status.setText("Marathon");
            lbl_status_mini.setText("Marathon");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Dashboard.fxml");
        } else if (event.getSource() == runner_btn) {
            lbl_status.setText("Runner");
            lbl_status_mini.setText("Runner");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Runner-view.fxml");
        } else if (event.getSource() == sponsor_btn) {
            lbl_status.setText("Sponsor");
            lbl_status_mini.setText("Sponsor");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == participation_btn) {
            lbl_status.setText("Participation");
            lbl_status_mini.setText("Participation");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Participation-view.fxml");
            Stage stage = (Stage) participation_btn.getScene().getWindow();
            stage.close();
        } else if (event.getSource() == chrono_btn) {
            lbl_status.setText("Chrono");
            lbl_status_mini.setText("Chrono");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == dashboard_btn) {
            lbl_status.setText("Dashboard");
            lbl_status_mini.setText("Dashboard");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Dashboard.fxml");
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
    private void insert_runner() {
        String name = name_text.getText();
        int age = Integer.parseInt(age_text.getText());
        String phone = phone_text.getText();
        String gender = gender_text.getText();
        String email = email_text.getText();

        // Insert values into the database
        try {
            Connection conn = Db_Connect.Connect_Db();
            if (conn != null) {
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO runner (name, age, phone, gender, email) " +
                                "VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, name);
                statement.setInt(2, age);
                statement.setString(3, phone);
                statement.setString(4, gender);
                statement.setString(5, email);
                statement.executeUpdate();
                clearFields();
                initialize_Runner();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert the runner into the database.");
        }
    }

    @FXML
    private void updateRunner(ActionEvent actionEvent) {
        try (Connection con = Db_Connect.Connect_Db();
             PreparedStatement stmt = con.prepareStatement("UPDATE runner SET name = ?, age = ?, phone = ?, gender = ?, email = ? WHERE runner_id = ?")) {

            stmt.setString(1, name_text.getText());
            stmt.setInt(2, Integer.parseInt(age_text.getText()));
            stmt.setString(3, phone_text.getText());
            stmt.setString(4, gender_text.getText());
            stmt.setString(5, email_text.getText());
            stmt.setInt(6, Integer.parseInt(runner_id_text.getText()));

            stmt.executeUpdate();

            // Refresh the runner table
            initialize_Runner();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update runner" + e.getMessage());
        }
    }

    @FXML
    public void deleteRunner(ActionEvent actionEvent) {
        try (Connection con = Db_Connect.Connect_Db();
             PreparedStatement stmt = con.prepareStatement("DELETE FROM runner WHERE runner_id = ?")) {

            stmt.setInt(1, Integer.parseInt(runner_id_text.getText()));

            stmt.executeUpdate();

            // Refresh the runner table
            initialize_Runner();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete runner" + e.getMessage());
        }
    }
    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void addnew_runner() {
        lbl_runner.setVisible(false);
        runner_id_text.setVisible(false);
        insert_btn.setVisible(true);
        addnewBtn.setVisible(false);
        clearFields();
    }

    @FXML
    void getSelected(MouseEvent event) {
        int index = runnerTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        runner_id_text.setText(runnerIdColumn.getCellData(index).toString());
        name_text.setText(nameColumn.getCellData(index).toString());
        age_text.setText(ageColumn.getCellData(index).toString());
        phone_text.setText(phoneColumn.getCellData(index).toString());
        gender_text.setText(genderColumn.getCellData(index).toString());
        email_text.setText(emailColumn.getCellData(index).toString());
    }
    private void clearFields() {
        runner_id_text.clear();
        name_text.clear();
        age_text.clear();
        phone_text.clear();
        gender_text.clear();
        email_text.clear();
    }


    private void showInterface(String name) throws Exception {
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
}



