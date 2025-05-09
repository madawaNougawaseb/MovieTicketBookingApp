package database;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class testConnection {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Database connected successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}