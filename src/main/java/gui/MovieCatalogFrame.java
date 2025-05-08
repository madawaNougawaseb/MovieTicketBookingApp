package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.net.URL;

public class MovieCatalogFrame extends javax.swing.JFrame {

    public MovieCatalogFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setTitle("Movie Catalog");
        setBackground(new java.awt.Color(18, 18, 18));
        setPreferredSize(new java.awt.Dimension(900, 600));
        setLayout(new java.awt.BorderLayout());

        JLabel titleLabel = new JLabel("Movie Catalog", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBackground(new Color(30, 30, 30));
        titleLabel.setOpaque(true);
        add(titleLabel, BorderLayout.PAGE_START);

        JPanel movieGridPanel = new JPanel();
        movieGridPanel.setBackground(new Color(18, 18, 18));
        movieGridPanel.setLayout(new GridLayout(0, 3, 20, 20));

        // Add movie cards dynamically
        movieGridPanel.add(createMovieCard("The Wolf Of Wall Street", "13+", "120 min", "images/wolf of wall street.jpeg"));
        movieGridPanel.add(createMovieCard("Sinners", "16+", "148 min", "images/sinners.jpeg"));
        movieGridPanel.add(createMovieCard("One Of Them Days", "R", "169 min", "images/download.jpeg"));

        JScrollPane movieScrollPane = new JScrollPane(movieGridPanel);
        add(movieScrollPane, BorderLayout.CENTER);

        Button bookButton = new Button("Book");
        bookButton.setBackground(new Color(0, 174, 239));
        bookButton.setFont(new Font("Dialog", Font.BOLD, 14));
        bookButton.setForeground(Color.WHITE);
        bookButton.addActionListener((ActionEvent evt) -> {
            SeatSelectionFrame seatSelectionFrame = new SeatSelectionFrame();
            seatSelectionFrame.setVisible(true);
            this.dispose(); // Close the current frame
        });
        add(bookButton, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMovieCard(String title, String ageRestriction, String duration, String posterPath) {
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(new Color(30, 30, 30));
        cardPanel.setBorder(BorderFactory.createLineBorder(new Color(44, 44, 44)));
        cardPanel.setPreferredSize(new Dimension(200, 310));
        cardPanel.setLayout(new BorderLayout());

        // Attempt to load the image resource
        URL imageUrl = getClass().getClassLoader().getResource(posterPath);
        JLabel posterLabel;
        if (imageUrl != null) {
            posterLabel = new JLabel(new ImageIcon(imageUrl), SwingConstants.CENTER);
        } else {
            // Fallback image or error message if the resource is unavailable
            posterLabel = new JLabel("Image not found", SwingConstants.CENTER);
            posterLabel.setForeground(Color.RED);
        }
        cardPanel.add(posterLabel, BorderLayout.PAGE_START);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(30, 30, 30));
        infoPanel.setLayout(new GridLayout(2, 1));
        infoPanel.add(new JLabel("Age Restriction: " + ageRestriction));
        infoPanel.add(new JLabel("Duration: " + duration));
        cardPanel.add(infoPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cardPanel.add(titleLabel, BorderLayout.PAGE_END);

        return cardPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovieCatalogFrame().setVisible(true));
    }
}