package com.student.studentapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showLogin();
    }

    public static void showLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/com/student/studentapp/login.fxml"));
        Scene scene = new Scene(loader.load(), 400, 300);
        primaryStage.setTitle("Student App - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showDashboard() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/com/student/studentapp/dashboard.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        primaryStage.setTitle("Student App - Dashboard");
        primaryStage.setScene(scene);
    }

    public static void showStudents() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                MainApp.class.getResource("/com/student/studentapp/student.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        primaryStage.setTitle("Student App - Student Management");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}