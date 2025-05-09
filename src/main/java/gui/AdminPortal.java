package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import model.Movie;

public class AdminPortal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<Movie> movies;
    private List<String> activityLogs; // List to store activity logs

    public AdminPortal() {
        setTitle("Admin Portal - TK Cinema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Initialize movie list and activity logs
        movies = new ArrayList<>();
        activityLogs = new ArrayList<>();
        movies.add(new Movie("The Wolf Of Wall Street", "13+", "120 min", "images/wolf_of_wall_street.jpeg"));
        movies.add(new Movie("Sinners", "16+", "148 min", "images/sinners.jpeg"));

        // Card layout for switching between login and dashboard
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add login and dashboard panels
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createDashboardPanel(), "Dashboard");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(30, 30, 30));

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.WHITE);
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.WHITE);

        JTextField txtUsername = new JTextField(20);
        JPasswordField txtPassword = new JPasswordField(20);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> handleLogin(txtUsername.getText(), new String(txtPassword.getPassword())));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(lblUsername, gbc);

        gbc.gridx = 1;
        loginPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        loginPanel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(btnLogin, gbc);

        return loginPanel;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(new Color(30, 30, 30));

        JLabel lblHeader = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblHeader.setForeground(new Color(100, 150, 255));
        dashboardPanel.add(lblHeader, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(30, 30, 30));

        JButton btnViewMovies = new JButton("View Movies");
        btnViewMovies.addActionListener(e -> showMovies());

        JButton btnAddMovie = new JButton("Add Movie");
        btnAddMovie.addActionListener(e -> addMovie());

        JButton btnEditMovie = new JButton("Edit Movie");
        btnEditMovie.addActionListener(e -> editMovie());

        JButton btnDeleteMovie = new JButton("Delete Movie");
        btnDeleteMovie.addActionListener(e -> deleteMovie());

        JButton btnViewLogs = new JButton("View User Activity");
        btnViewLogs.addActionListener(e -> viewUserActivity());

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        buttonPanel.add(btnViewMovies);
        buttonPanel.add(btnAddMovie);
        buttonPanel.add(btnEditMovie);
        buttonPanel.add(btnDeleteMovie);
        buttonPanel.add(btnViewLogs);
        buttonPanel.add(btnLogout);

        dashboardPanel.add(buttonPanel, BorderLayout.CENTER);

        return dashboardPanel;
    }

    private void handleLogin(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            cardLayout.show(mainPanel, "Dashboard");
            activityLogs.add("Admin logged in.");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMovies() {
        StringBuilder movieList = new StringBuilder("Movies:\n");
        for (Movie movie : movies) {
            movieList.append(movie.getTitle()).append(" - ").append(movie.getAgeRestriction())
                    .append(" - ").append(movie.getDuration()).append("\n");
        }
        JOptionPane.showMessageDialog(this, movieList.toString(), "Movie List", JOptionPane.INFORMATION_MESSAGE);
        activityLogs.add("Viewed movie list.");
    }

    private void addMovie() {
        JTextField txtTitle = new JTextField();
        JTextField txtAgeRestriction = new JTextField();
        JTextField txtDuration = new JTextField();
        JTextField txtPosterPath = new JTextField();

        Object[] message = {
                "Title:", txtTitle,
                "Age Restriction:", txtAgeRestriction,
                "Duration:", txtDuration,
                "Poster Path:", txtPosterPath
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Movie", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String title = txtTitle.getText().trim();
            String ageRestriction = txtAgeRestriction.getText().trim();
            String duration = txtDuration.getText().trim();
            String posterPath = txtPosterPath.getText().trim();

            if (!title.isEmpty() && !ageRestriction.isEmpty() && !duration.isEmpty() && !posterPath.isEmpty()) {
                movies.add(new Movie(title, ageRestriction, duration, posterPath));
                JOptionPane.showMessageDialog(this, "Movie added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                activityLogs.add("Added movie: " + title);
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editMovie() {
        String movieTitle = JOptionPane.showInputDialog(this, "Enter the title of the movie to edit:");
        if (movieTitle != null) {
            for (Movie movie : movies) {
                if (movie.getTitle().equalsIgnoreCase(movieTitle)) {
                    JTextField txtTitle = new JTextField(movie.getTitle());
                    JTextField txtAgeRestriction = new JTextField(movie.getAgeRestriction());
                    JTextField txtDuration = new JTextField(movie.getDuration());
                    JTextField txtPosterPath = new JTextField(movie.getPosterPath());

                    Object[] message = {
                            "Title:", txtTitle,
                            "Age Restriction:", txtAgeRestriction,
                            "Duration:", txtDuration,
                            "Poster Path:", txtPosterPath
                    };

                    int option = JOptionPane.showConfirmDialog(this, message, "Edit Movie", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        movie.setTitle(txtTitle.getText().trim());
                        movie.setAgeRestriction(txtAgeRestriction.getText().trim());
                        movie.setDuration(txtDuration.getText().trim());
                        movie.setPosterPath(txtPosterPath.getText().trim());
                        JOptionPane.showMessageDialog(this, "Movie updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        activityLogs.add("Edited movie: " + movieTitle);
                    }
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Movie not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMovie() {
        String movieTitle = JOptionPane.showInputDialog(this, "Enter the title of the movie to delete:");
        if (movieTitle != null) {
            movies.removeIf(movie -> movie.getTitle().equalsIgnoreCase(movieTitle));
            JOptionPane.showMessageDialog(this, "Movie deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            activityLogs.add("Deleted movie: " + movieTitle);
        }
    }

    private void viewUserActivity() {
        StringBuilder logList = new StringBuilder("User Activity Logs:\n");
        for (String log : activityLogs) {
            logList.append(log).append("\n");
        }
        JOptionPane.showMessageDialog(this, logList.toString(), "Activity Logs", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPortal().setVisible(true));
    }
}