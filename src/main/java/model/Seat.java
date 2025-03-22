package model;

public class Seat {
    private int seatId;
    private int screenId;
    private String seatNumber;
    private boolean isVip;

    //Constructor
    public Seat(int seatId, int screenId, String seatNumber, boolean isVip) {
        this.seatId = seatId;
        this.screenId = screenId;
        this.seatNumber = seatNumber;
        this.isVip = isVip;
    }
    //getters
    public int getSeatId() {
        return seatId;
    }

    public int getScreenId() {
        return screenId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    //Setters
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    //To check if seat is VIP
    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean isVip) {
        this.isVip = isVip;
    }
}
