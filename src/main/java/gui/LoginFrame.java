package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("Login - TK Cinema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(new Color(30, 30, 30));

        JLabel heading = new JLabel("TK Cinema", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 40));
        heading.setForeground(new Color(100, 150, 255));
        heading.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        rootPanel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new Color(100, 150, 255));
        formPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        userField = new JTextField(20);
        formPanel.add(userField, gbc);

        // Password field  
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(new Color(100, 150, 255));
        formPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        passField = new JPasswordField(20);
        formPanel.add(passField, gbc);

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(50, 100, 200));
        loginButton.setForeground(Color.WHITE);
        formPanel.add(loginButton, gbc);

        // Login Action
        loginButton.addActionListener(e -> handleLogin());

        // Register label
        gbc.gridy = 3;
        JLabel registerLabel = new JLabel("New user? Register here");
        registerLabel.setForeground(new Color(100, 150, 255));
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(registerLabel, gbc);

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new RegistrationFrame().setVisible(true);
            }
        });

        rootPanel.add(formPanel, BorderLayout.CENTER);
        add(rootPanel);
    }

   private void handleLogin() {
       String input = userField.getText().trim(); // Can be username or email
       String password = new String(passField.getPassword()).trim();

       if (input.isEmpty() || password.isEmpty()) {
           JOptionPane.showMessageDialog(this, "Enter username/email and password.", "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }

       try (Connection conn = DatabaseConnection.getConnection()) {
           String query = "SELECT name FROM Users WHERE (name = ? OR email = ?) AND password = ?";
           try (PreparedStatement stmt = conn.prepareStatement(query)) {
               stmt.setString(1, input);
               stmt.setString(2, input);
               stmt.setString(3, password);

               try (ResultSet rs = stmt.executeQuery()) {
                   if (rs.next()) {
                       String username = rs.getString("name");
                       JOptionPane.showMessageDialog(this, "Login successful!");
                       dispose();
                       new DashboardFrame(username).setVisible(true);
                   } else {
                       JOptionPane.showMessageDialog(this, "Invalid username/email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                   }
               }
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
           JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
       }
   }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}