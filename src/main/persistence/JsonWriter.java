package persistence;

import model.BookList;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// represents a writer that writes booklist data into a save file
public class JsonWriter {

    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer
    //          if destination file is not found, throws FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of booklists to file
    public void write(BookList rb, BookList fb) {
        JSONObject outer = new JSONObject();
        JSONObject readBooks = rb.toJson();
        JSONObject favoriteBooks = fb.toJson();
        outer.put("readBooks", readBooks);
        outer.put("favoriteBooks", favoriteBooks);
        saveToFile(outer.toString());
    }

    // EFFECTS: saves file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }
}