package model;

public class Booking {
    private int bookingId;
    private int userId;
    private int showtimeId;
    private int seatId;
    private String status;

    //Constructor
    public Booking(int bookingId, int userId, int showtimeId, int seatId, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.status = status;
    }

    //getter
    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public String getStatus() {
        return status;
    }

    //setter
    public void setStatus(String status) {
        this.status = status;
    }
}
