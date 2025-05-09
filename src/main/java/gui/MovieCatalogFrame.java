package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MovieCatalogFrame extends JFrame {

    private JTextField txtSearch;
    private JPanel movieGridPanel;
    private List<Movie> movies;

    public MovieCatalogFrame() {
        setTitle("Search Movies - TK Cinema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Initialize movie list
        movies = new ArrayList<>();
        movies.add(new Movie("The Wolf Of Wall Street", "13+", "120 min", "images/wolf_of_wall_street.jpeg"));
        movies.add(new Movie("Sinners", "16+", "148 min", "images/sinners.jpeg"));
        movies.add(new Movie("One Of Them Days", "R", "169 min", "images/one_of_them_days.jpeg"));

        // Root panel
        JPanel rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBackground(new Color(30, 30, 30));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel lblHeader = new JLabel("Search Movies", SwingConstants.CENTER);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblHeader.setForeground(new Color(100, 150, 255));
        rootPanel.add(lblHeader, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBackground(new Color(30, 30, 30));

        txtSearch = new JTextField(30);
        txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtSearch.setBackground(new Color(20, 20, 20));
        txtSearch.setForeground(Color.WHITE);
        txtSearch.setCaretColor(Color.WHITE);
        txtSearch.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 255)));

        JButton btnSearch = new JButton("Search");
        styleButton(btnSearch);
        btnSearch.addActionListener(e -> filterMovies());

        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        rootPanel.add(searchPanel, BorderLayout.NORTH);

        // Movie catalog panel
        movieGridPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        movieGridPanel.setBackground(new Color(30, 30, 30));
        populateMovies(movies);

        JScrollPane scrollPane = new JScrollPane(movieGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        rootPanel.add(scrollPane, BorderLayout.CENTER);

        add(rootPanel);
    }

    private void populateMovies(List<Movie> movieList) {
        movieGridPanel.removeAll();
        for (Movie movie : movieList) {
            movieGridPanel.add(createMovieCard(movie));
        }
        movieGridPanel.revalidate();
        movieGridPanel.repaint();
    }

    private JPanel createMovieCard(Movie movie) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(new Color(20, 20, 20));
        cardPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 255)));

        // Poster
        JLabel posterLabel = new JLabel();
        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        posterLabel.setOpaque(true); // Ensure background color is applied
        posterLabel.setBackground(new Color(20, 20, 20)); // Match panel background
        try {
            ImageIcon icon = new ImageIcon(movie.getPosterPath());
            Image scaledImage = icon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
            posterLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            posterLabel.setText("Image not found");
            posterLabel.setForeground(Color.RED);
        }
        cardPanel.add(posterLabel, BorderLayout.CENTER);

        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(new Color(20, 20, 20));

        JLabel lblTitle = new JLabel(movie.getTitle(), SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblAge = new JLabel("Age Restriction: " + movie.getAgeRestriction(), SwingConstants.CENTER);
        lblAge.setForeground(Color.LIGHT_GRAY);

        JLabel lblDuration = new JLabel("Duration: " + movie.getDuration(), SwingConstants.CENTER);
        lblDuration.setForeground(Color.LIGHT_GRAY);

        infoPanel.add(lblTitle);
        infoPanel.add(lblAge);
        infoPanel.add(lblDuration);

        cardPanel.add(infoPanel, BorderLayout.SOUTH);

        // Select button
        JButton btnSelect = new JButton("Select");
        styleButton(btnSelect);
        btnSelect.addActionListener(e -> {
            new SeatSelectionFrame(movie.getTitle()).setVisible(true);
            dispose();
        });
        cardPanel.add(btnSelect, BorderLayout.NORTH);

        return cardPanel;
    }

    private void filterMovies() {
        String query = txtSearch.getText().trim().toLowerCase();
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(query)) {
                filteredMovies.add(movie);
            }
        }
        populateMovies(filteredMovies);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(0, 174, 239));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MovieCatalogFrame().setVisible(true));
    }
}

// Movie class to hold movie details
class Movie {
    private String title;
    private String ageRestriction;
    private String duration;
    private String posterPath;

    public Movie(String title, String ageRestriction, String duration, String posterPath) {
        this.title = title;
        this.ageRestriction = ageRestriction;
        this.duration = duration;
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public String getDuration() {
        return duration;
    }

    public String getPosterPath() {
        return posterPath;
    }
}