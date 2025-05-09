package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionFrame extends JFrame {

    // Constants
    private static final int ROWS = 8;
    private static final int COLS = 10;
    private static final Color AVAILABLE_COLOR = new Color(144, 238, 144); // Light green
    private static final Color SELECTED_COLOR = new Color(100, 149, 237);  // Cornflower blue
    private static final Color BOOKED_COLOR = new Color(211, 211, 211);    // Light gray

    // Components
    private JPanel mainPanel, seatPanel, infoPanel, controlPanel;
    private JButton[][] seats;
    private JButton btnConfirm, btnCancel, btnToPayment;
    private JLabel lblScreen, lblMovieInfo, lblTimeInfo, lblSelectedSeats, lblTotalPrice;

    // Data
    private boolean[][] bookedSeats;
    private List<String> selectedSeatsList;
    private double ticketPrice = 12.50;  // Default price
    private String movieName;
    private String theaterName = "Cinema 3";
    private String showTime = "May 8, 2025 - 7:30 PM";

    public SeatSelectionFrame(String movieName) {
        this.movieName = movieName;

        // Set up the frame
        setTitle("Cinema System - Seat Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Initialize data
        initializeData();

        // Create components
        createComponents();

        // Set layout
        layoutComponents();

        // Add action listeners
        addEventHandlers();
    }

    private void initializeData() {
        // Initialize booked seats (randomly for demo)
        bookedSeats = new boolean[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                bookedSeats[i][j] = Math.random() < 0.3; // 30% chance of being booked
            }
        }

        // Initialize selected seats list
        selectedSeatsList = new ArrayList<>();
    }

    private void createComponents() {
        // Create panels
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        seatPanel = new JPanel(new GridLayout(ROWS, COLS, 5, 5));
        infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Create seat buttons
        seats = new JButton[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                seats[i][j] = new JButton();
                seats[i][j].setPreferredSize(new Dimension(50, 40));
                seats[i][j].setName(i + "," + j);  // Store seat position in the name

                // Set initial color based on availability
                if (bookedSeats[i][j]) {
                    seats[i][j].setBackground(BOOKED_COLOR);
                    seats[i][j].setEnabled(false);
                } else {
                    seats[i][j].setBackground(AVAILABLE_COLOR);
                }

                // Add a label to show seat number
                String seatLabel = (char) ('A' + i) + String.valueOf(j + 1);
                seats[i][j].setText(seatLabel);
                seats[i][j].setFocusPainted(false);
            }
        }

        // Create labels
        lblScreen = new JLabel("SCREEN", JLabel.CENTER);
        lblScreen.setFont(new Font("Arial", Font.BOLD, 18));
        lblScreen.setOpaque(true);
        lblScreen.setBackground(Color.LIGHT_GRAY);
        lblScreen.setPreferredSize(new Dimension(400, 30));

        lblMovieInfo = new JLabel("Movie: " + movieName + " | Theater: " + theaterName);
        lblTimeInfo = new JLabel("Show Time: " + showTime);
        lblSelectedSeats = new JLabel("Selected Seats: None");
        lblTotalPrice = new JLabel("Total Price: $0.00");

        // Create buttons
        btnConfirm = new JButton("Confirm Selection");
        btnCancel = new JButton("Cancel");
        btnToPayment = new JButton("Proceed to Payment");
    }

    private void layoutComponents() {
        // Add seats to seat panel
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                seatPanel.add(seats[i][j]);
            }
        }

        // Add labels to info panel
        infoPanel.add(lblMovieInfo);
        infoPanel.add(lblTimeInfo);
        infoPanel.add(lblSelectedSeats);
        infoPanel.add(lblTotalPrice);

        // Add buttons to control panel
        controlPanel.add(btnConfirm);
        controlPanel.add(btnCancel);
        controlPanel.add(btnToPayment);

        // Add screen to the top
        JPanel screenPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        screenPanel.add(lblScreen);

        // Add legend
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        JPanel availableKey = new JPanel();
        availableKey.setBackground(AVAILABLE_COLOR);
        availableKey.setPreferredSize(new Dimension(20, 20));
        legendPanel.add(availableKey);
        legendPanel.add(new JLabel("Available"));

        JPanel selectedKey = new JPanel();
        selectedKey.setBackground(SELECTED_COLOR);
        selectedKey.setPreferredSize(new Dimension(20, 20));
        legendPanel.add(selectedKey);
        legendPanel.add(new JLabel("Selected"));

        JPanel bookedKey = new JPanel();
        bookedKey.setBackground(BOOKED_COLOR);
        bookedKey.setPreferredSize(new Dimension(20, 20));
        legendPanel.add(bookedKey);
        legendPanel.add(new JLabel("Booked"));

        // Add components to main panel
        mainPanel.add(screenPanel, BorderLayout.NORTH);
        mainPanel.add(seatPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(legendPanel, BorderLayout.NORTH);
        southPanel.add(infoPanel, BorderLayout.CENTER);
        southPanel.add(controlPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
    }

    private void addEventHandlers() {
        // Add action listeners to seat buttons
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                final int row = i;
                final int col = j;
                seats[i][j].addActionListener(e -> toggleSeatSelection(row, col));
            }
        }

        // Add action listener to confirm button
        btnConfirm.addActionListener(e -> confirmBooking());

        // Add action listener to cancel button
        btnCancel.addActionListener(e -> cancelBooking());

        // Add action listener to navigate to PaymentFrame
        btnToPayment.addActionListener(e -> {
            if (selectedSeatsList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select at least one seat.", "No Seats Selected", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double total = selectedSeatsList.size() * ticketPrice;
            String selectedSeats = String.join(", ", selectedSeatsList);
            new PaymentFrame(selectedSeats, total).setVisible(true);
            dispose();
        });
    }

    private void toggleSeatSelection(int row, int col) {
        JButton seat = seats[row][col];
        String seatLabel = seat.getText();

        if (seat.getBackground().equals(AVAILABLE_COLOR)) {
            seat.setBackground(SELECTED_COLOR);
            selectedSeatsList.add(seatLabel);
        } else if (seat.getBackground().equals(SELECTED_COLOR)) {
            seat.setBackground(AVAILABLE_COLOR);
            selectedSeatsList.remove(seatLabel);
        }

        updateSelectionInfo();
    }

    private void updateSelectionInfo() {
        if (selectedSeatsList.isEmpty()) {
            lblSelectedSeats.setText("Selected Seats: None");
            lblTotalPrice.setText("Total Price: $0.00");
            btnConfirm.setEnabled(false);
        } else {
            String[] seatArray = selectedSeatsList.toArray(new String[0]);
            java.util.Arrays.sort(seatArray);

            lblSelectedSeats.setText("Selected Seats: " + String.join(", ", seatArray));
            double total = selectedSeatsList.size() * ticketPrice;
            lblTotalPrice.setText(String.format("Total Price: $%.2f", total));
            btnConfirm.setEnabled(true);
        }
    }

    private void confirmBooking() {
        if (selectedSeatsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one seat.", "No Seats Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = selectedSeatsList.size() * ticketPrice;
        String message = String.format(
                "Booking confirmed!\n\nMovie: %s\nTheater: %s\nTime: %s\nSeats: %s\nTotal: $%.2f",
                movieName, theaterName, showTime, String.join(", ", selectedSeatsList), total);

        JOptionPane.showMessageDialog(this, message, "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (seats[i][j].getBackground().equals(SELECTED_COLOR)) {
                    seats[i][j].setBackground(BOOKED_COLOR);
                    seats[i][j].setEnabled(false);
                    bookedSeats[i][j] = true;
                }
            }
        }

        selectedSeatsList.clear();
        updateSelectionInfo();
    }

    private void cancelBooking() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (seats[i][j].getBackground().equals(SELECTED_COLOR)) {
                    seats[i][j].setBackground(AVAILABLE_COLOR);
                }
            }
        }

        selectedSeatsList.clear();
        updateSelectionInfo();

        JOptionPane.showMessageDialog(this, "Seat selection cancelled.", "Selection Cancelled", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SeatSelectionFrame("Sample Movie").setVisible(true));
    }
}