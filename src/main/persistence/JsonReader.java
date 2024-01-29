package persistence;


import model.Book;
import model.BookList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads booklist data from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads booklist from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public BookList readReadBooks() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReadBookList(jsonObject);
    }

    // EFFECTS: reads favorite booklist from file and returns it;
    //          throws IO exception if an error occurs reading data from file
    public BookList readFavoriteBooks() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFavoriteBookList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        String str = contentBuilder.toString();
        return contentBuilder.toString();
    }

    // EFFECTS: parses read book list
    private BookList parseReadBookList(JSONObject jsonObject) {
        JSONObject readBooks = jsonObject.getJSONObject("readBooks");
        BookList rb = new BookList("readBooks");
        addBookList(rb, readBooks);
        return rb;
    }

    // EFFECTS: parses favorite book list
    private BookList parseFavoriteBookList(JSONObject jsonObject) {
        JSONObject favoriteBooks = jsonObject.getJSONObject("favoriteBooks");
        BookList fb = new BookList("favoriteBooks");
        addBookList(fb, favoriteBooks);
        return fb;
    }

    // EFFECTS: parses a booklist from JSON object and adds it to the list of book lists.
    private void addBookList(BookList bookList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("booklists");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(bookList, nextBook);
        }
    }

    // EFFECTS: parses a book from JSON object and adds it to a booklist
    private void addBook(BookList bookList, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        int year = jsonObject.getInt("year");
        String genre = jsonObject.getString("genre");
        String author = jsonObject.getString("author");
        int rating = jsonObject.getInt("rating");
        Book book = new Book(title, year, genre, author, rating);
        bookList.addBook(book);
    }
}