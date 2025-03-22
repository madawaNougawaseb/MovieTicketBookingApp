package model;
import java.util.Date;
public class Movie {
    //fields
    private int movieId;
    private String title;
    private String genre;
    private int duration;
    private double rating;
    private String description;
    private Date releaseDate;
    private String posterUrl;

    //constructor
    public Movie(int movieId, String title, String genre, int duration, double rating, String description, Date releaseDate, String posterUrl) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.description = description;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
    }
    //getters
    public int getMovieId() { return movieId; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getDuration() { return duration; }
    public double getRating() { return rating; }
    public String getDescription() { return description; }
    public Date getReleaseDate() { return releaseDate; }
    public String getPosterUrl() { return posterUrl; }
}

