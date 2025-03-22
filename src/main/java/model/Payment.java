package model;
import java.util.Date;
public class Payment {
    private int paymentId;
    private int bookingId;
    private double amount;
    private Date paymentDate;
    private String paymentStatus;

    public Payment(int paymentId, int bookingId, double amount, Date paymentDate, String paymentStatus) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    //getters
    public int getPaymentId() {
        return paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }
    public Date getPaymentDate() {
        return paymentDate;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }

    //setters
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
