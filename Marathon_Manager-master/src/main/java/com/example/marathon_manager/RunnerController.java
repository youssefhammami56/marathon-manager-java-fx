package com.example.marathon_manager;

import Model.Db_Connect;
import Model.Marathon;
import Model.Runner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RunnerController implements Initializable {
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
    private TextField last_name_text;

    @FXML
    private TextField email_text;

    @FXML
    private TableView<Runner> runnerTable;

    @FXML
    private TableColumn<Runner, Integer> runnerIdColumn;

    @FXML
    private TableColumn<Marathon,String> MarathonNameColumn;

    @FXML
    private TableColumn<Runner, String> nameColumn;

    @FXML
    private TableColumn<Runner, String> lastNameColu;
    @FXML
    private TableColumn<Runner, Integer> ageColumn;

    @FXML
    private TableColumn<Runner, String> phoneColumn;

    @FXML
    private TableColumn<Runner, String> genderColumn;

    @FXML
    private TableColumn<Runner, String> emailColumn;

    @FXML
    private ComboBox marathon_combo;

    @FXML
    private Button show_btn;

    @FXML
    private Button btn_close;

    @FXML
    private Button deleteBtn;



    @FXML
    private Button updateBtn;

    @FXML
    private Button rankingBtn;

    public static int Ma_Id = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(LoginviewController.role.equals("user")){
            runnerTable.setVisible(false);
            show_btn.setVisible(false);
            deleteBtn.setVisible(false);
            updateBtn.setVisible(false);
            marathon_btn.setVisible(false);
            participation_btn.setVisible(false);
            sponsor_btn.setVisible(false);
            insert_btn.setVisible(false);
            chrono_btn.setVisible(false);
            rankingBtn.setVisible(false);



        }

    }

    public void initialize_Runner() {
        // Set up table columns
        runnerIdColumn.setCellValueFactory(new PropertyValueFactory<>("runnerId"));
        MarathonNameColumn.setCellValueFactory(new PropertyValueFactory<>("marathonName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColu.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        // Set up data in the table
        runnerTable.setItems(getRunners());
    }

    public  ObservableList<Runner> getRunners() {

        ObservableList<Runner> runners = FXCollections.observableArrayList();

        runners.clear();
        try (
                Connection con = Db_Connect.Connect_Db();
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM runner WHERE marathon_id = ? ");
                PreparedStatement stmt2 = con.prepareStatement("SELECT name FROM marathon WHERE marathon_id = ?");



        ) {
            stmt.setInt(1,getidMarathon());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                System.out.println("Runner table loaded.");
                int runnerId = resultSet.getInt("runner_id");
                int marathonId = resultSet.getInt("marathon_id");
                String name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                String phone = resultSet.getString("phone_number");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                stmt2.setInt(1, marathonId);
                ResultSet resultSet2 = stmt2.executeQuery();
                if (resultSet2.next()) {
                    String marathonName = resultSet2.getString("name");
                    System.out.println(marathonName);
                    Runner runner = new Runner(runnerId, name, last_name, marathonId, marathonName, age,gender,email,phone);
                    runners.add(runner);
                }
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
            showInterface("Marathon-view.fxml");
            Stage stage = (Stage) marathon_btn.getScene().getWindow();
            stage.close();
        } else if (event.getSource() == runner_btn) {
            lbl_status.setText("Runner");
            lbl_status_mini.setText("Runner");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Runner-view.fxml");
            Stage stage = (Stage) runner_btn.getScene().getWindow();
            stage.close();
        } else if (event.getSource() == sponsor_btn) {
            lbl_status.setText("Sponsor");
            lbl_status_mini.setText("Sponsor");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Sponsor-view.fxml");
            Stage stage = (Stage) sponsor_btn.getScene().getWindow();
            stage.close();
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
            showInterface("Chrono-view.fxml");
            Stage stage = (Stage) chrono_btn.getScene().getWindow();
            stage.close();
        } else if (event.getSource() == dashboard_btn) {
            lbl_status.setText("Dashboard");
            lbl_status_mini.setText("Dashboard");
            pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
            showInterface("Dashboard.fxml");
            Stage stage = (Stage) dashboard_btn.getScene().getWindow();
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
    private void insert_runner() {

        String name = name_text.getText();
        String last_name = last_name_text.getText();
        int age = Integer.parseInt(age_text.getText());
        String phone = phone_text.getText();
        String gender = gender_text.getText();
        String email = email_text.getText();
        String marathon_name = marathon_combo.getValue().toString();

        // Insert values into the database
        try {

                Connection con = Db_Connect.Connect_Db();
                PreparedStatement stmt = con.prepareStatement("SELECT marathon_id FROM marathon WHERE name = ?");
                stmt.setString(1, marathon_name); // Set the name parameter
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    Ma_Id = resultSet.getInt("marathon_id");
                }
                if (con != null) {
                    PreparedStatement statement = con.prepareStatement(
                            "INSERT INTO runner (first_name, age, phone_number, gender, email,marathon_id,last_name,Payment_status) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ? , 'Not paid')");
                    statement.setString(1, name);
                    statement.setInt(2, age);
                    statement.setString(3, phone);
                    statement.setString(4, gender);
                    statement.setString(5, email);
                    System.out.println(Ma_Id);
                    statement.setInt(6, Ma_Id);
                    statement.setString(7, last_name);
                    statement.executeUpdate();
                    //clearFields();
                    initialize_Runner();
                    clearFields();
                    showAlert("Success", "your request has been sent successfully");
                }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to insert the runner into the database.");
        }
    }

    @FXML
    private void updateRunner(ActionEvent actionEvent) {
        System.out.println(Ma_Id);
        try (Connection con = Db_Connect.Connect_Db();
             PreparedStatement stmt = con.prepareStatement("UPDATE runner SET first_name = ?, age = ?, phone_number = ?, gender = ?, email = ?,marathon_id = ?,last_name = ? WHERE runner_id = ?")) {

            stmt.setString(1, name_text.getText());
            stmt.setInt(2, Integer.parseInt(age_text.getText()));
            stmt.setString(3, phone_text.getText());
            stmt.setString(4, gender_text.getText());
            stmt.setString(5, email_text.getText());
            stmt.setInt(6,getidMarathon());
            stmt.setString(7, last_name_text.getText());
            stmt.setInt(8, Integer.parseInt(runner_id_text.getText()));

            stmt.executeUpdate();

            // Refresh the runner table
            initialize_Runner();
            clearFields();
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
            clearFields();
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



    private void clearFields() {
        runner_id_text.clear();
        name_text.clear();
        last_name_text.clear();
        marathon_combo.setValue("Marathon Name");
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


    public void Marathon_Name_Combobox() {

        String s = "";
        if (marathon_combo.getValue() != null) {
            s = (String) marathon_combo.getValue();
            System.out.println(s);
        } else {
            System.out.println("No value selected");
        }
    }

    @FXML
    public void select_Combobox(javafx.scene.input.MouseEvent mouseEvent) {
        try (Connection con = Db_Connect.Connect_Db();
             PreparedStatement stmt = con.prepareStatement("Select marathon_id,name  FROM marathon ")) {
            ObservableList data = FXCollections.observableArrayList();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                data.add(resultSet.getString("name"));
            }
            marathon_combo.setItems(data);

        } catch ( SQLException e) {
            System.out.println("Error");
        }
    }

    public void getSelectedColu(javafx.scene.input.MouseEvent mouseEvent) {

        int index = runnerTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        runner_id_text.setText(runnerIdColumn.getCellData(index).toString());
        name_text.setText(nameColumn.getCellData(index).toString());
        last_name_text.setText(lastNameColu.getCellData(index).toString());
        marathon_combo.setValue(MarathonNameColumn.getCellData(index).toString());
        age_text.setText(ageColumn.getCellData(index).toString());
        phone_text.setText(phoneColumn.getCellData(index).toString());
        gender_text.setText(genderColumn.getCellData(index).toString());
        email_text.setText(emailColumn.getCellData(index).toString());

    }


    public int getidMarathon(){
        String marathon_name = marathon_combo.getValue().toString();
        try (
        Connection con = Db_Connect.Connect_Db();
        PreparedStatement stmt = con.prepareStatement("SELECT marathon_id FROM marathon WHERE name = ?")){
        stmt.setString(1, marathon_name); // Set the name parameter
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            Ma_Id =  resultSet.getInt("marathon_id");
        }
        System.out.println(Ma_Id);
    }catch (SQLException e) {
        e.printStackTrace();
    }
        return Ma_Id;
    }


    public void closerun(MouseEvent mouseEvent) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }

    private boolean areFieldsEmpty(){
        return name_text.getText().isEmpty()||last_name_text.getText().isEmpty()||marathon_combo.getValue().toString().isEmpty()||age_text.getText().isEmpty()||phone_text.getText().isEmpty()||gender_text.getText().isEmpty()||email_text.getText().isEmpty();
    }
}



