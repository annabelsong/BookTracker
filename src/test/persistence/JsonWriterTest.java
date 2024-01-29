package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// SOURCE: JsonSerializationDemo
//

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            BookList rb = new BookList("readBooks");
            BookList fb = new BookList("favoriteBooks");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBookLists() {
        try {
            BookList rb = new BookList("readBooks");
            BookList fb = new BookList("favoriteBooks");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookLists.json");
            writer.open();
            writer.write(rb, fb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBookLists.json");
            rb = reader.readReadBooks();
            fb = reader.readFavoriteBooks();
            assertEquals("readBooks", rb.getName());
            assertEquals("favoriteBooks", fb.getName());
            assertEquals(0, rb.getBooks().size());
            assertEquals(0, fb.getBooks().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBookLists() {
        try {
            BookList rb = new BookList("readBooks");
            BookList fb = new BookList("favoriteBooks");
            rb.addBook(new Book("hello", 2001, "hello", "adele", 10));
            rb.addBook(new Book("bye", 2002, "bye", "adele2", 10));
            fb.addBook(new Book("bye", 2002, "bye", "adele2", 10));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookLists.json");
            writer.open();
            writer.write(rb, fb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBookLists.json");
            rb = reader.readReadBooks();
            fb = reader.readFavoriteBooks();
            assertEquals("readBooks", rb.getName());
            assertEquals("favoriteBooks", fb.getName());
            List<Book> readBooks = rb.getBooks();
            List<Book> favoriteBooks = fb.getBooks();
            assertEquals(2, readBooks.size());
            checkBook("hello", 2001, "hello", "adele", 10, readBooks.get(0));
            checkBook("bye", 2002, "bye", "adele2", 10, readBooks.get(1));
            checkBook("bye", 2002, "bye", "adele2", 10, favoriteBooks.get(0));


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}