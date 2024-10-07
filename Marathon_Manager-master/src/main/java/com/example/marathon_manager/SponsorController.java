package com.example.marathon_manager;

import Model.Db_Connect;
import Model.Sponsor;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;



import Model.Sponsor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

    public class SponsorController {

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
        private TextField sponsorIdField;
        @FXML
        private TextField nameField;
        @FXML
        private TextField sponsorshipAmountField;
        @FXML
        private ImageView logoImageView;
        @FXML
        private TableView<Sponsor> sponsorTableView;

        @FXML
        private TableColumn<Sponsor, Integer> sponsorIdColumn;

        @FXML
        private TableColumn<Sponsor, String> nameColumn;

        @FXML
        private TableColumn<Sponsor, String> logoColumn;

        @FXML
        private TableColumn<Sponsor, Double> sponsor_amount;

        private ObservableList<Sponsor> sponsorList;
        private File logoFile;

        private Connection connection;
        private PreparedStatement insertStatement;
        private PreparedStatement updateStatement;
        private PreparedStatement deleteStatement;



        public void initialize_Sponsor() {
            System.out.println("Sponsor Controller initialized.");
            // Set up table columns
            sponsorIdColumn.setCellValueFactory(new PropertyValueFactory<Sponsor, Integer>("sponsorId"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<Sponsor, String>("name"));
            logoColumn.setCellValueFactory(new PropertyValueFactory<Sponsor, String>("logo"));
            sponsor_amount.setCellValueFactory(new PropertyValueFactory<Sponsor, Double>("sponsorshipAmount"));

            // Set up data in the table
            sponsorTableView.setItems(loadSponsors());
        }

        public  ObservableList<Sponsor> loadSponsors() {
            ObservableList<Sponsor> sponsorList = FXCollections.observableArrayList();
            try {
                // Connect to the database


                Connection con = Db_Connect.Connect_Db();
                assert con != null;
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM sponsor ");
                    ResultSet resultSet = stmt.executeQuery();
                    while (resultSet.next()) {
                        System.out.println("sponsor table loaded.");
                        int sponsor_id = resultSet.getInt("sponsor_id");
                        String name = resultSet.getString("name");
                        String logo = resultSet.getString("logo");
                        double sponsorship_amount = resultSet.getDouble("sponsorship_amount");
                        Sponsor sponsor = new Sponsor(sponsor_id, name, logo, sponsorship_amount);
                        sponsorList.add(sponsor);
                        }


                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to load the runner into the database.");
                }

                return sponsorList;
            }


        @FXML
        private void handleAddButtonAction(ActionEvent event) {


            try {

                Connection con = Db_Connect.Connect_Db();
                if (con != null) {
                 PreparedStatement stmt = con.prepareStatement( "INSERT INTO `sponsor` (`name`, `logo`, `sponsorship_amount`) VALUES ( ?, ?, ?);");


            stmt.setString(1, nameField.getText());
            stmt.setDouble(3, Double.parseDouble(sponsorshipAmountField.getText()));
            stmt.setString(2, "testLogo");


            stmt.executeUpdate();
            initialize_Sponsor();
            clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle any exceptions here
            }
        }





        @FXML
        private void handleTableViewMouseClickedAction() {
            // Get selected sponsor from the TableView
            Sponsor selectedSponsor = sponsorTableView.getSelectionModel().getSelectedItem();

            if (selectedSponsor != null) {
                // Populate the form fields with selected sponsor data
                sponsorIdField.setText(String.valueOf(selectedSponsor.getSponsorId()));
                nameField.setText(selectedSponsor.getName());
                sponsorshipAmountField.setText(String.valueOf(selectedSponsor.getSponsorshipAmount()));

                // Load and display the logo image

            }
        }

        @FXML
        private void updateSponsor(ActionEvent actionEvent) {

            try (Connection con = Db_Connect.Connect_Db()) {
                assert con != null;
                try (PreparedStatement stmt = con.prepareStatement("UPDATE `sponsor` SET `name` = ?, `logo` = ?,`sponsorship_amount` = ? WHERE  `sponsor`.`sponsor_id` = ? ")) {

                    stmt.setString(1, nameField.getText());
                    stmt.setString(2, "testLogo");
                    stmt.setDouble(3, Double.parseDouble(sponsorshipAmountField.getText()));
                    stmt.setInt(4, Integer.parseInt(sponsorIdField.getText()));

                    stmt.executeUpdate();
                        initialize_Sponsor();
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to update runner" + e.getMessage());
            }
        }


        @FXML
        private void deleteSponsor(ActionEvent event) {
            try (Connection con = Db_Connect.Connect_Db();
                 PreparedStatement stmt = con.prepareStatement("delete from sponsor WHERE sponsor_id = ?")) {


                stmt.setInt(1, Integer.parseInt(sponsorIdField.getText()));

                stmt.executeUpdate();
                initialize_Sponsor();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to update runner" + e.getMessage());
            }
        }

        @FXML
        private void handleLogoUploadButtonAction(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Upload Logo");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
            );

            File selectedFile = fileChooser.showOpenDialog(logoImageView.getScene().getWindow());
            if (selectedFile != null) {
                logoFile = selectedFile;
                Image image = new Image(selectedFile.toURI().toString());
                logoImageView.setImage(image);
            }
        }



        private void clearFields() {
            sponsorIdField.clear();
            nameField.clear();
            sponsorshipAmountField.clear();
            logoImageView.setImage(null);
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

        private static void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
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

        public boolean isSponsorFieldEmpty() {
            return nameField.getText().isEmpty() || sponsorshipAmountField.getText().isEmpty();}

}
