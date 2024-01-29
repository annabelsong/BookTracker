package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    Book book1;
    Book book2;

    @BeforeEach
    public void setUp() {
        book1 = new Book("title1", 0, "genre1", "author1", 0);
    }

    @Test
    public void testConstructor() {
        assertEquals("title1", book1.getTitle());
        assertEquals(0, book1.getYear());
        assertEquals("genre1", book1.getGenre());
        assertEquals("author1", book1.getAuthor());
        assertEquals(0, book1.getRating());
    }

    @Test
    public void testEmptyConstructor() {
        Book emptyBook = new Book();
        assertEquals("", emptyBook.getTitle());
        assertEquals(0, emptyBook.getYear());
        assertEquals("", emptyBook.getGenre());
        assertEquals("", emptyBook.getAuthor());
        assertEquals(0, emptyBook.getRating());
    }

    @Test
    public void changeBookRating() {
        book2 = new Book("title2", 0, "genre2", "author2", 0);

        assertEquals(0, book1.getRating());
        Book.changeBookRating(book1, 9);
        book2.setRating(9);
        assertEquals(9, book1.getRating());
        assertEquals(book1.getRating(), book2.getRating());

        Book.changeBookRating(book2, 6);
        assertEquals(6, book2.getRating());
    }

}