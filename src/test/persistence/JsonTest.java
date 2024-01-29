package persistence;

import model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkBook(String title, int year, String genre, String author, int rating, Book book) {
        assertEquals(title, book.getTitle());
        assertEquals(year, book.getYear());
        assertEquals(genre, book.getGenre());
        assertEquals(author, book.getAuthor());
        assertEquals(rating, book.getRating());
    }
}
