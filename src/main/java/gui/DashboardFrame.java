package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Import your GUI components
import gui.MovieCatalogFrame;
import gui.SeatSelectionFrame;

public class DashboardFrame extends JFrame {

    public DashboardFrame(String username) {
        setTitle("Dashboard - TK Cinema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(new Color(30, 30, 30));

        // Heading
        JLabel heading = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 40));
        heading.setForeground(new Color(100, 150, 255));
        heading.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        rootPanel.add(heading, BorderLayout.NORTH);

        // Center Buttons Panel
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        centerPanel.setBackground(new Color(30, 30, 30));

        JButton searchMoviesButton = new JButton("Search Movies");
        styleButton(searchMoviesButton);

        JButton selectSeatsButton = new JButton("Select Seats");
        styleButton(selectSeatsButton);

        centerPanel.add(searchMoviesButton);
        centerPanel.add(selectSeatsButton);

        rootPanel.add(centerPanel, BorderLayout.CENTER);
        add(rootPanel);

        // Action: Open MovieCatalogFrame
        searchMoviesButton.addActionListener((ActionEvent e) -> {
            new MovieCatalogFrame().setVisible(true);
            dispose();
        });

        // Action: Open SeatSelectionFrame
        selectSeatsButton.addActionListener((ActionEvent e) -> {
            new SeatSelectionFrame("").setVisible(true);
            dispose();
        });
    }

    // Utility method to style buttons
    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 22));
        button.setBackground(new Color(50, 100, 200));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardFrame("User").setVisible(true));
    }
}

