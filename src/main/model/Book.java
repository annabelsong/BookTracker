package model;

import org.json.JSONObject;
import persistence.Writeable;

// Represents a Book with a title, year, genre, author and rating (out of 10)
public class Book implements Writeable {
    private String title;
    private int year;
    private String genre;
    private String author;
    private int rating;

    // EFFECTS: creates a new instance of a book with default title, year, genre, author, and rating
    public Book() {
        this.title = "";
        this.year = 0;
        this.genre = "";
        this.author = "";
        this.rating = 0;
    }

    // EFFECTS: creates a new instance of a book with the given title, year, genre, author, and rating (out of 10)
    public Book(String title, int year, String genre, String author, int rating) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.author = author;
        this.rating = rating;
    }

    // MODIFIES: this
    // EFFECTS: changes the given book's rating to the new rating
    public static void changeBookRating(Book book, int newRating) {
        book.setRating(newRating);
    }

    // SETTERS
    public void setRating(int rating) {
        this.rating = rating;
    }

    // GETTERS
    public String getTitle() {
        return this.title;
    }

    public int getYear() {
        return this.year;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getRating() {
        return this.rating;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("year", year);
        json.put("genre", genre);
        json.put("author", author);
        json.put("rating", rating);

        return json;
    }
}