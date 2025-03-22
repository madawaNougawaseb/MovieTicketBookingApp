package authentication;
import database.DatabaseConnection;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class userAuthentication {

    //Registers new user In Database
    public boolean registerUser(String name, String email, String password, String phone, String userType) {
        String hashedPassword = hashPassword(password); // Hash the password before storing
        String query = "INSERT INTO Users (name, email, password, phone, user_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);
            stmt.setString(4, phone);
            stmt.setString(5, userType);
            stmt.executeUpdate(); // Execute the insert query
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //authenticates user by checking the entered email and passed and compare it with what's in the database
    public boolean loginUser(String email, String password) {
        String query = "SELECT password FROM Users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password"); // Retrieve stored hashed password
                return storedHash.equals(hashPassword(password)); // Compare hashed passwords
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Hashes a password using SHA-256 encryption.
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // Get SHA-256 instance
            byte[] hash = md.digest(password.getBytes()); // Compute hash
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b)); // Convert hash to hex string
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}

