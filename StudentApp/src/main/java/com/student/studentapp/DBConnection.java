package com.student.studentapp;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final Dotenv dotenv = Dotenv.load();

    public static Connection connect() {
        try {
            return DriverManager.getConnection(
                    dotenv.get("DB_URL"),
                    dotenv.get("DB_USER"),
                    dotenv.get("DB_PASSWORD")
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}