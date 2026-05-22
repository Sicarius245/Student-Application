package com.student.studentapp;

import javafx.beans.property.*;

public class Student {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty studentNumber = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty course = new SimpleStringProperty();
    private IntegerProperty yearLevel = new SimpleIntegerProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();

    public Student(int id, String studentNumber, String firstName, String lastName,
                   String course, int yearLevel, String email, String phone) {
        this.id.set(id);
        this.studentNumber.set(studentNumber);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.course.set(course);
        this.yearLevel.set(yearLevel);
        this.email.set(email);
        this.phone.set(phone);
    }

    public int getId() { return id.get(); }
    public String getStudentNumber() { return studentNumber.get(); }
    public String getFirstName() { return firstName.get(); }
    public String getLastName() { return lastName.get(); }
    public String getCourse() { return course.get(); }
    public int getYearLevel() { return yearLevel.get(); }
    public String getEmail() { return email.get(); }
    public String getPhone() { return phone.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty studentNumberProperty() { return studentNumber; }
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty lastNameProperty() { return lastName; }
    public StringProperty courseProperty() { return course; }
    public IntegerProperty yearLevelProperty() { return yearLevel; }
    public StringProperty emailProperty() { return email; }
    public StringProperty phoneProperty() { return phone; }
}
