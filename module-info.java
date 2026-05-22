module com.student.studentapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.student.studentapp to javafx.fxml;
    exports com.student.studentapp;
}