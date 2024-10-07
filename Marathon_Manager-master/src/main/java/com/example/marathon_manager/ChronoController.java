package com.example.marathon_manager;



import Model.ChronoAgent;
import Model.Db_Connect;
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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChronoController {
        @FXML
        private Button addnewBtn;

        @FXML
        private TextField chronoAgent_id_text;

        @FXML
        private TableColumn<?, ?> chronoIdColumn;

        @FXML
        private TableView<ChronoAgent> chronoTable;

        @FXML
        private Button chrono_btn;


        @FXML
        private TextField chronopassword;

        @FXML
        private Button deleteBtn;

        @FXML
        private Button insert_btn;
        @FXML
        private Button dashboard_btn;

        @FXML
        private Label lbl_marathon;

        @FXML
        private Label lbl_status;

        @FXML
        private Label lbl_status_mini;

        @FXML
        private ComboBox<String> marathcombo;

        @FXML
        private TableColumn<?, ?> marathonColumn;

        @FXML
        private Button marathon_btn;

        @FXML
        private TableColumn<?, ?> nameColumn;

        @FXML
        private TextField name_text;

        @FXML
        private Button participation_btn;

        @FXML
        private Pane pnl_status;

        @FXML
        private Button runner_btn;

        @FXML
        private Button show_btn;

        @FXML
        private Button sponsor_btn;

        @FXML
        private Button sponsor_btn1;

        @FXML
        private Button test_btn;

        @FXML
        private Button updateBtn;

        @FXML
        void Marathon_Name_Combobox(ActionEvent event) {

        }

        public static int Ma_Id = 0;

        public void initialize_Agent() {
            // Set up table columns
            chronoIdColumn.setCellValueFactory(new PropertyValueFactory<>("agentId"));
            marathonColumn.setCellValueFactory(new PropertyValueFactory<>("marathonId"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            System.out.println("initialize_Agent");


            // Set up data in the table
            chronoTable.setItems(getChronoAgent());
        }

        public  ObservableList<ChronoAgent> getChronoAgent() {

            ObservableList<ChronoAgent> agents = FXCollections.observableArrayList();

            agents.clear();
            try (
                    Connection con = Db_Connect.Connect_Db();
                    PreparedStatement stmt = con.prepareStatement("SELECT * FROM chrono_agent where marathon_id = ?");
                    PreparedStatement stmt2 = con.prepareStatement("SELECT name FROM marathon WHERE marathon_id = ?");



            ) {
                System.out.println("Ma_Id = " + getidMarathon());
                stmt.setInt(1, getidMarathon());
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    System.out.println("chrono_agent table loaded.");
                    int chronoAgentId = resultSet.getInt("agent_id");
                    int marathonId = resultSet.getInt("marathon_id");
                    String name = resultSet.getString("agent_name");
                    String password = resultSet.getString("CIN");
                    stmt2.setInt(1, marathonId);
                    ResultSet resultSet2 = stmt2.executeQuery();
                    if (resultSet2.next()) {
                        String marathonName = resultSet2.getString("name");
                        System.out.println(marathonName);
                        ChronoAgent chronoAgent = new ChronoAgent(chronoAgentId, name, marathonId,password);
                        agents.add(chronoAgent);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load the agent from the database.");
            }

            return agents;
        }

        @FXML
        private void handleButtonAction(ActionEvent event) throws Exception {
            if (event.getSource() == marathon_btn) {
                lbl_status.setText("Marathon");
                lbl_status_mini.setText("Marathon");
                pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
                showInterface("Marathon-view.fxml");
            } else if (event.getSource() == runner_btn) {
                lbl_status.setText("Runner");
                lbl_status_mini.setText("Runner");
                pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
                showInterface("Runner-view.fxml");
            } else if (event.getSource() == sponsor_btn) {
                lbl_status.setText("Sponsor");
                lbl_status_mini.setText("Sponsor");
                pnl_status.setBackground(new Background(new BackgroundFill(Color.rgb(29, 38, 125), CornerRadii.EMPTY, Insets.EMPTY)));
                showInterface("Sponsor-view.fxml");
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
        private void insert_agents() {

            String name = name_text.getText();
            int password = Integer.parseInt(chronopassword.getText());
            String marathon_name = marathcombo.getValue().toString();

            // Insert values into the database
            try {
                Connection con = Db_Connect.Connect_Db();
                PreparedStatement stmt = con.prepareStatement("SELECT marathon_id FROM marathon WHERE name = ?");
                stmt.setString(1, marathon_name); // Set the name parameter
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    Ma_Id =  resultSet.getInt("marathon_id");
                }
                if (con != null) {
                    PreparedStatement statement = con.prepareStatement(
                            "INSERT INTO chrono_agent (agent_name, cin,marathon_id) " +
                                    "VALUES (?, ?, ?)");
                    statement.setString(1, name);
                    statement.setInt(2, password);
                    System.out.println(Ma_Id);
                    statement.setInt(3, Ma_Id);
                    statement.executeUpdate();
                    //clearFields();
                    initialize_Agent();
                    insert_btn.setDisable(false);
                    addnewBtn.setDisable(true);
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to insert the chrono into the database.");
            }
        }

        @FXML
        private void updateagent(ActionEvent actionEvent) {
            System.out.println(Ma_Id);
            try (Connection con = Db_Connect.Connect_Db();
                 PreparedStatement stmt = con.prepareStatement("UPDATE chrono_agent SET agent_name = ?, CIN = ?, marathon_id = ? WHERE agent_id = ?")) {

                stmt.setString(1, name_text.getText());
                stmt.setInt(2, Integer.parseInt(chronopassword.getText()));
                stmt.setInt(3,getidMarathon());
                stmt.setInt(4, Integer.parseInt(chronoAgent_id_text.getText()));

                stmt.executeUpdate();

                // Refresh the runner table
                initialize_Agent();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to update chrono" + e.getMessage());
            }
        }

        @FXML
        public void deleteagent(ActionEvent actionEvent) {
            try (Connection con = Db_Connect.Connect_Db();
                 PreparedStatement stmt = con.prepareStatement("DELETE FROM chrono_agent WHERE agent_id = ?")) {

                stmt.setInt(1, Integer.parseInt(chronoAgent_id_text.getText()));

                stmt.executeUpdate();

                // Refresh the runner table
                initialize_Agent();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete chrono" + e.getMessage());
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
        private void addnew_agent() {
            lbl_marathon.setVisible(false);
            chronoAgent_id_text.setVisible(false);
            insert_btn.setVisible(true);
            addnewBtn.setVisible(false);
            clearFields();
        }



        private void clearFields() {
            chronoAgent_id_text.clear();
            name_text.clear();
            chronopassword.clear();
            marathcombo.setValue("Marathon Name");

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


        public void Marathon_Name_Combobox() {

            String s = "";
            if (marathcombo.getValue() != null) {
                s = (String) marathcombo.getValue();
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
                marathcombo.setItems(data);

            } catch ( SQLException e) {
                System.out.println("Error");
            }
        }

        public void getSelectedColu(javafx.scene.input.MouseEvent mouseEvent) {

            int index = chronoTable.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            chronoAgent_id_text.setText(chronoIdColumn.getCellData(index).toString());
            name_text.setText(nameColumn.getCellData(index).toString());
            marathcombo.setValue(marathonColumn.getCellData(index).toString());

        }


        public int getidMarathon(){
            String marathon_name = marathcombo.getValue().toString();
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
    }

