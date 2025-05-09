package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PaymentFrame extends JFrame {

    private JLabel lblTotalPrice, lblSelectedSeats;
    private JTextField txtCardNumber, txtCardHolderName, txtExpiryDate, txtCVV;
    private JButton btnConfirmPayment, btnCancel;

    public PaymentFrame(String selectedSeats, double totalPrice) {
        setTitle("Payment - TK Cinema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Root panel
        JPanel rootPanel = new JPanel(new BorderLayout(10, 10));
        rootPanel.setBackground(new Color(30, 30, 30));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel lblHeader = new JLabel("Payment Details", SwingConstants.CENTER);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblHeader.setForeground(new Color(100, 150, 255));
        rootPanel.add(lblHeader, BorderLayout.NORTH);

        // Center panel for payment form
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(30, 30, 30));

        lblSelectedSeats = new JLabel("Seats: " + selectedSeats);
        lblSelectedSeats.setForeground(Color.WHITE);
        lblTotalPrice = new JLabel(String.format("Total: $%.2f", totalPrice));
        lblTotalPrice.setForeground(Color.WHITE);

        JLabel lblCardNumber = new JLabel("Card Number:");
        lblCardNumber.setForeground(Color.WHITE);
        txtCardNumber = new JTextField();

        JLabel lblCardHolderName = new JLabel("Card Holder Name:");
        lblCardHolderName.setForeground(Color.WHITE);
        txtCardHolderName = new JTextField();

        JLabel lblExpiryDate = new JLabel("Expiry Date (MM/YY):");
        lblExpiryDate.setForeground(Color.WHITE);
        txtExpiryDate = new JTextField();

        JLabel lblCVV = new JLabel("CVV:");
        lblCVV.setForeground(Color.WHITE);
        txtCVV = new JTextField();

        formPanel.add(lblSelectedSeats);
        formPanel.add(lblTotalPrice);
        formPanel.add(lblCardNumber);
        formPanel.add(txtCardNumber);
        formPanel.add(lblCardHolderName);
        formPanel.add(txtCardHolderName);
        formPanel.add(lblExpiryDate);
        formPanel.add(txtExpiryDate);
        formPanel.add(lblCVV);
        formPanel.add(txtCVV);

        rootPanel.add(formPanel, BorderLayout.CENTER);

        // Footer panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(30, 30, 30));

        btnConfirmPayment = new JButton("Confirm Payment");
        styleButton(btnConfirmPayment);
        btnConfirmPayment.addActionListener(this::handleConfirmPayment);

        btnCancel = new JButton("Cancel");
        styleButton(btnCancel);
        btnCancel.addActionListener(e -> {
            new SeatSelectionFrame("Movie Title").setVisible(true);
            dispose();
        });

        buttonPanel.add(btnConfirmPayment);
        buttonPanel.add(btnCancel);

        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(rootPanel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(0, 174, 239));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void handleConfirmPayment(ActionEvent e) {
        String cardNumber = txtCardNumber.getText().trim();
        String cardHolderName = txtCardHolderName.getText().trim();
        String expiryDate = txtExpiryDate.getText().trim();
        String cvv = txtCVV.getText().trim();

        if (cardNumber.isEmpty() || cardHolderName.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Payment successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        new DashboardFrame("User").setVisible(true); // Redirect to dashboard
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentFrame("A1, A2", 25.00).setVisible(true));
    }
}