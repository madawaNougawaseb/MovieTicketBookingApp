package model;

public class Movie {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}