package com.example.marathon_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginviewController {


    @FXML
    private AnchorPane login;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField passwordL;

    @FXML
    private PasswordField PassSignUp;

    @FXML
    private TextField username;


    @FXML
    private Button login_button;

    @FXML
    private Button close;



    @FXML
    private Button CreateAccountBtn;

    @FXML
    private Label CreateAccountLbl;

    @FXML
    private TextField EmailTxt;

    @FXML
    private AnchorPane LognAnchor;

    @FXML
    private PasswordField ConfirmPasswordTxt;

    @FXML
    private TextField username11;

    @FXML
    private AnchorPane SingUpAnchor;

    public static String role;

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet result;

    public void loginAdmin() {
            String sql = "SELECT * FROM users WHERE user_name=? AND password=?";


        try {
            conn = Model.Db_Connect.Connect_Db();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username.getText());
            ps.setString(2, passwordL.getText());
            result = ps.executeQuery();
            Alert alert;
            if (username.getText().isEmpty() || passwordL.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please enter username and password");
                alert.showAndWait();
            } else if (result.next()) {
                role = result.getString("user_role");
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Login Successful");
                alert.showAndWait();
                System.out.println(role);
                showDashboardInterface();

            } else {

                showAlert(Alert.AlertType.ERROR, "Error", "Invalid username or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signUpUser() {

        if(areFieldsEmptySignUp() == false) {

            String username = username11.getText();
            String password = PassSignUp.getText();
            String confirmPassword = ConfirmPasswordTxt.getText();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter all fields.");
                return;
            } else if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
                return;
            } else if (password.length() < 8) {
                showAlert(Alert.AlertType.ERROR, "Error", "Password should be at least 8 characters long.");
                return;
            } else {
                try {
                    String sql = "INSERT INTO users (user_name, password ,user_role) VALUES (?, ? , 'user')";
                    conn = Model.Db_Connect.Connect_Db();

                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    int rowsInserted = statement.executeUpdate();

                    if (rowsInserted > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Sign up successful!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to insert user.");
                    }
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Database error: " + e.getMessage());
                }
            }

            // TODO: Perform additional validation if necessary

            // TODO: Save the user to the database or perform other actions

            showAlert(Alert.AlertType.INFORMATION, "Success", "Sign up successful!");
        }else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter all fields.");
        }
        // Clear the text fields
        username11.clear();
        PassSignUp.clear();
        ConfirmPasswordTxt.clear();
    }




    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showDashboardInterface() throws Exception {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }

    }





    public void closelog() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void CreateAcount(ActionEvent actionEvent) {

    }



    public void Back(MouseEvent mouseEvent) {
        SingUpAnchor.setVisible(false);
        LognAnchor.setVisible(true);
    }

    public void SignUpLbl(MouseEvent mouseEvent) {
        SingUpAnchor.setVisible(true);
        LognAnchor.setVisible(false);
    }

    public void showpass(MouseEvent mouseEvent) {
        passwordL.setManaged(true);
    }

    private boolean areFieldsEmpty() {
        return username.getText().isEmpty() || passwordL.getText().isEmpty();
    }

    private boolean areFieldsEmptySignUp() {
        return username11.getText().isEmpty() || PassSignUp.getText().isEmpty() || ConfirmPasswordTxt.getText().isEmpty();
    }
}
