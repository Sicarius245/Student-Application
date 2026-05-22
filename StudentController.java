package com.student.studentapp;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class StudentController {

    @FXML private TextField txtStudentNumber, txtFirstName, txtLastName;
    @FXML private TextField txtCourse, txtYear, txtEmail, txtPhone;
    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, String> colStudentNumber, colFirstName,
            colLastName, colCourse, colEmail, colPhone;
    @FXML private TableColumn<Student, Integer> colYear;
    @FXML private Label lblStatus;

    private ObservableList<Student> list = FXCollections.observableArrayList();
    private int selectedId = -1;

    @FXML
    public void initialize() {
        colStudentNumber.setCellValueFactory(d -> d.getValue().studentNumberProperty());
        colFirstName.setCellValueFactory(d -> d.getValue().firstNameProperty());
        colLastName.setCellValueFactory(d -> d.getValue().lastNameProperty());
        colCourse.setCellValueFactory(d -> d.getValue().courseProperty());
        colYear.setCellValueFactory(d -> d.getValue().yearLevelProperty().asObject());
        colEmail.setCellValueFactory(d -> d.getValue().emailProperty());
        colPhone.setCellValueFactory(d -> d.getValue().phoneProperty());

        loadData();

        table.setOnMouseClicked(e -> {
            Student s = table.getSelectionModel().getSelectedItem();
            if (s != null) {
                selectedId = s.getId();
                txtStudentNumber.setText(s.getStudentNumber());
                txtFirstName.setText(s.getFirstName());
                txtLastName.setText(s.getLastName());
                txtCourse.setText(s.getCourse());
                txtYear.setText(String.valueOf(s.getYearLevel()));
                txtEmail.setText(s.getEmail());
                txtPhone.setText(s.getPhone());
            }
        });
    }

    private void loadData() {
        list.clear();
        try (Connection conn = DBConnection.connect()) {
            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT * FROM students ORDER BY id");
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("student_number"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("course"),
                        rs.getInt("year_level"),
                        rs.getString("email") != null ? rs.getString("email") : "",
                        rs.getString("phone") != null ? rs.getString("phone") : ""
                ));
            }
            table.setItems(list);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void addStudent() {
        if (!validateInputs()) return;
        try (Connection conn = DBConnection.connect()) {
            PreparedStatement pst = conn.prepareStatement(
                    "INSERT INTO students(student_number,first_name,last_name,course,year_level,email,phone) VALUES(?,?,?,?,?,?,?)");
            setParams(pst);
            pst.executeUpdate();
            lblStatus.setText("Student added successfully!");
            loadData(); clearFields();
        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void updateStudent() {
        if (selectedId == -1) { lblStatus.setText("Please select a student."); return; }
        if (!validateInputs()) return;
        try (Connection conn = DBConnection.connect()) {
            PreparedStatement pst = conn.prepareStatement(
                    "UPDATE students SET student_number=?,first_name=?,last_name=?,course=?,year_level=?,email=?,phone=? WHERE id=?");
            setParams(pst);
            pst.setInt(8, selectedId);
            pst.executeUpdate();
            lblStatus.setText("Student updated successfully!");
            loadData(); clearFields();
        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void deleteStudent() {
        if (selectedId == -1) { lblStatus.setText("Please select a student."); return; }
        try (Connection conn = DBConnection.connect()) {
            PreparedStatement pst = conn.prepareStatement(
                    "DELETE FROM students WHERE id=?");
            pst.setInt(1, selectedId);
            pst.executeUpdate();
            lblStatus.setText("Student deleted successfully!");
            loadData(); clearFields();
        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void clearFields() {
        txtStudentNumber.clear(); txtFirstName.clear(); txtLastName.clear();
        txtCourse.clear(); txtYear.clear(); txtEmail.clear(); txtPhone.clear();
        selectedId = -1;
        lblStatus.setText("");
    }

    @FXML
    private void goBack() {
        try { MainApp.showDashboard(); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void setParams(PreparedStatement pst) throws SQLException {
        pst.setString(1, txtStudentNumber.getText());
        pst.setString(2, txtFirstName.getText());
        pst.setString(3, txtLastName.getText());
        pst.setString(4, txtCourse.getText());
        pst.setInt(5, Integer.parseInt(txtYear.getText()));
        pst.setString(6, txtEmail.getText());
        pst.setString(7, txtPhone.getText());
    }

    private boolean validateInputs() {
        // Student Number format: 2xxx-0xxx-SR-x
        String studentNum = txtStudentNumber.getText().trim();
        if (!studentNum.matches("\\d{4}-\\d{4}-[A-Z]{2}-\\d")) {
            lblStatus.setText("Invalid Student No. Format: 2xxx-0xxx-SR-x (e.g. 2024-0001-SR-1)");
            lblStatus.setStyle("-fx-text-fill: red;");
            return false;
        }

        // First Name - letters only, no numbers
        String firstName = txtFirstName.getText().trim();
        if (firstName.isEmpty() || !firstName.matches("[a-zA-Z\\s\\-\\.]+")) {
            lblStatus.setText("First Name should contain letters only.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return false;
        }

        // Last Name - letters only, no numbers
        String lastName = txtLastName.getText().trim();
        if (lastName.isEmpty() || !lastName.matches("[a-zA-Z\\s\\-\\.]+")) {
            lblStatus.setText("Last Name should contain letters only.");
            lblStatus.setStyle("-fx-text-fill: red;");
            return false;
        }

        // Course - must be a valid course code
        String course = txtCourse.getText().trim();
        if (!course.matches("[A-Z]{2,4}(\\s[A-Z]{1,2})?") &&
                !course.matches("BS[A-Z]+|AB[A-Z]+|[A-Z]{2,10}")) {
            lblStatus.setText("Course should be a valid course code (e.g. BSIT, BSCS, BSEE).");
            lblStatus.setStyle("-fx-text-fill: red;");
            return false;
        }

        // Year Level - must be 1 to 4
        String year = txtYear.getText().trim();
        try {
            int y = Integer.parseInt(year);
            if (y < 1 || y > 4) {
                lblStatus.setText("Year Level must be between 1 and 4.");
                lblStatus.setStyle("-fx-text-fill: red;");
                return false;
            }
        } catch (NumberFormatException e) {
            lblStatus.setText("Year Level must be a number (1-4).");
            lblStatus.setStyle("-fx-text-fill: red;");
            return false;
        }

        // Email - valid format
        String email = txtEmail.getText().trim();
        if (!email.isEmpty() && !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            lblStatus.setText("Invalid email format (e.g. juan@email.com).");
            lblStatus.setStyle("-fx-text-fill: red;");
            return false;
        }

        // Phone - format: 09xx-xxx-xxxx
        String phone = txtPhone.getText().trim();
        if (!phone.isEmpty() && !phone.matches("09\\d{2}-\\d{3}-\\d{4}")) {
            lblStatus.setText("Invalid phone format. Use: 09xx-xxx-xxxx (e.g. 0912-345-6789).");
            lblStatus.setStyle("-fx-text-fill: red;");
            return false;
        }

        lblStatus.setStyle("-fx-text-fill: green;");
        return true;
    }
}