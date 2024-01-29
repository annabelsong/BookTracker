package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Represents a list of books that have been read, and a list of books that have been favorited
public class BookList implements Writeable {
    private String name;
    private List<Book> bookList;

    // EFFECTS: creates a new instance of a book list with an empty list of read books and an empty
    //          list of favorite books
    public BookList() {
        this.bookList = new LinkedList<Book>();
    }

    // EFFECTS: creates a new instance of a booklist with an empty list of read books and an empty
    //          list of favorite books with a name assigned
    public BookList(String name) {
        this.name = name;
        this.bookList = new LinkedList<Book>();
    }

    // MODIFIES: this
    // EFFECTS: adds book into the book list
    public void addBook(Book book) {
        bookList.add(book);
        EventLog.getInstance().logEvent(new Event("Added book to booklist : " + book.getTitle()));
    }

    // MODIFIES: this
    // EFFECTS: removes book from book list
    public void removeBook(Book book) {
        bookList.remove(book);
        EventLog.getInstance().logEvent(new Event("Removed book from booklist : " + book.getTitle()));
    }

    // EFFECTS: returns true if the bookList contains the given book
    public Boolean hasBook(String title) {
        List<String> bookListNames = new ArrayList<>();
        for (Book b : bookList) {
            bookListNames.add(b.getTitle());
        }
        return bookListNames.contains(title);
    }


    // MODIFIES: this
    // EFFECTS: outputs a list of all the books with the given genre
    public List<String> getBooksOfSameGenre(String genre) {
        List<String> genreBooks = new ArrayList<>();
        for (Book b : bookList) {
            if (b.getGenre().equals(genre)) {
                genreBooks.add(b.getTitle());
            }
        }
        System.out.println("You have read " + genreBooks.size() + " books of the " + genre + " genre:");
        System.out.println(genreBooks);
        return genreBooks;
    }

    // EFFECTS: returns a list of titles of all books in book list
    public List<String> getBookTitles() {
        LinkedList<String> listTitles = new LinkedList<>();
        for (Book b : this.bookList) {
            listTitles.add(b.getTitle());
        }
        return listTitles;
    }

    // SETTERS

    // GETTERS
    public List<Book> getBooks() {
        return bookList;
    }

    public String getName() {
        return name;
    }

    @Override
    // EFFECTS: returns as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", "My Book list application");
        json.put("booklists", booklistsToJson());
        return json;
    }

    // EFFECTS: writes booklist data as JSON
    private JSONArray booklistsToJson() {
        JSONArray jsonArrayBookList = new JSONArray();

        for (Book b : bookList) {
            jsonArrayBookList.put(b.toJson());
        }

        return jsonArrayBookList;

    }
}