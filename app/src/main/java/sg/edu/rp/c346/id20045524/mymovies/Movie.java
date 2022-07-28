package sg.edu.rp.c346.id20045524.mymovies;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String genre;
    private int year;
    private String rating;

    public Movie(int id, String title, String genre, int year, String rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRating() { return rating; }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int ratingInt(String rating){
        int position = 0;
        switch (rating){
            case "G":
                position = 0;
                break;
            case "PG":
                position = 1;
                break;
            case "PG13":
                position = 2;
                break;
            case "NC16":
                position = 3;
                break;
            case "M18":
                position = 4;
                break;
            case "r21":
                position = 5;
                break;
        }
        return position;
    }

}
