package com.student.studentapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Please enter username and password.");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?");
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                MainApp.showDashboard();
            } else {
                lblError.setText("Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblError.setText("Connection error.");
        }
    }
}
