package model;

public class Cinema {
    private final int theatreId;
    private String name;
    private String location;
    //Constructor
    public Cinema(int theatreId, String name, String location) {
        this.theatreId = theatreId;
        this.name = name;
        this.location = location;
    }
    //getters
    public int getTheatreId() {
        return theatreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
