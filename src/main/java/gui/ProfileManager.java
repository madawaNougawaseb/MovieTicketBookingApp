import javax.swing.*;
import java.awt.*;

public class ProfileManager extends JFrame {

    private JTextField nameField, emailField, phoneField;
    private JButton saveButton;

    public ProfileManager() {
        setTitle("Profile Manager");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color darkGrey = new Color(30, 30, 30);
        Color darkerGrey = new Color(20, 20, 20);
        Color teal = new Color(0, 128, 128);
        Color white = Color.WHITE;

        // Apply global dark theme to all components
        UIManager.put("Panel.background", darkGrey);
        UIManager.put("Label.foreground", white);
        UIManager.put("Button.background", teal);
        UIManager.put("Button.foreground", white);
        UIManager.put("TextField.background", darkerGrey);
        UIManager.put("TextField.foreground", white);
        UIManager.put("TextField.caretForeground", white);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(teal);
        JLabel titleLabel = new JLabel("User Profile");
        titleLabel.setForeground(white);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel phoneLabel = new JLabel("Phone:");

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        saveButton = new JButton("Save");
        saveButton.setOpaque(true);
        saveButton.setBorderPainted(false);

        saveButton.addActionListener(e -> saveProfile());

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(new JLabel());
        formPanel.add(saveButton);

        getContentPane().setBackground(darkGrey);
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void saveProfile() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        JOptionPane.showMessageDialog(this,
                "Profile Saved!\nName: " + name + "\nEmail: " + email + "\nPhone: " + phone,
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProfileManager::new);
    }
}
