package com.student.studentapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.sql.*;

public class DashboardController {

    @FXML private Label lblTotalStudents;
    @FXML private Label lblTotalCourses;

    @FXML
    public void initialize() {
        loadStats();
    }

    private void loadStats() {
        try (Connection conn = DBConnection.connect()) {
            ResultSet rs1 = conn.createStatement()
                    .executeQuery("SELECT COUNT(*) FROM students");
            if (rs1.next())
                lblTotalStudents.setText(String.valueOf(rs1.getInt(1)));

            ResultSet rs2 = conn.createStatement()
                    .executeQuery("SELECT COUNT(DISTINCT course) FROM students");
            if (rs2.next())
                lblTotalCourses.setText(String.valueOf(rs2.getInt(1)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToStudents() {
        try { MainApp.showStudents(); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleLogout() {
        try {
            MainApp.showLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
