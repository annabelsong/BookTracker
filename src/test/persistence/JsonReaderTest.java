package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BookList readBooks = reader.readReadBooks();
            BookList favoriteBooks = reader.readFavoriteBooks();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBookLists() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBookLists.json");
        try {
            BookList rb = reader.readReadBooks();
            BookList fb = reader.readFavoriteBooks();
            assertEquals("readBooks", rb.getName());
            assertEquals("favoriteBooks", fb.getName());
            assertEquals(0, rb.getBooks().size());
            assertEquals(0, fb.getBooks().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBookList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBookLists.json");
        try {
            BookList rb = reader.readReadBooks();
            BookList fb = reader.readFavoriteBooks();
            assertEquals("readBooks", rb.getName());
            assertEquals("favoriteBooks", fb.getName());
            List<Book> readBooks = rb.getBooks();
            List<Book> favoriteBooks = fb.getBooks();
            assertEquals(2, readBooks.size());
            assertEquals(1, favoriteBooks.size());
            checkBook("hello", 2001, "hello", "adele", 10, readBooks.get(0));
            checkBook("bye", 2002, "bye", "adele2", 10, readBooks.get(1));
            checkBook("bye", 2002, "bye", "adele2", 10, favoriteBooks.get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}