package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationFrame extends JFrame {

    public RegistrationFrame() {
        setTitle("Register - TK Cinema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Root layout
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(new Color(30, 30, 30));

        // Heading
        JLabel heading = new JLabel("TK Cinema - Register", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 36));
        heading.setForeground(new Color(100, 150, 255));
        heading.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        rootPanel.add(heading, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(new Color(100, 150, 255));
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(20);
        formPanel.add(usernameField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(100, 150, 255));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(100, 150, 255));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setForeground(new Color(100, 150, 255));
        formPanel.add(confirmLabel, gbc);

        gbc.gridx = 1;
        JPasswordField confirmField = new JPasswordField(20);
        formPanel.add(confirmField, gbc);

        // Register Button
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(50, 100, 200));
        registerButton.setForeground(Color.WHITE);
        formPanel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();
            String confirm = new String(confirmField.getPassword()).trim();

            if (username.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "{CALL RegisterUser(?, ?, ?, ?, ?)}";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, username);
                    stmt.setString(2, email);
                    stmt.setString(3, pass); // Password hashing should be added here.
                    stmt.setString(4, null); // Leave phone field empty for now.
                    stmt.setString(5, "customer"); // Default user type.

                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    dispose();
                    new LoginFrame().setVisible(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Link to Login
        gbc.gridy = 5;
        JLabel loginLink = new JLabel("Already have an account? Login here");
        loginLink.setForeground(new Color(100, 150, 255));
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(loginLink, gbc);

        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        rootPanel.add(formPanel, BorderLayout.CENTER);
        add(rootPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegistrationFrame().setVisible(true);
        });
    }
}