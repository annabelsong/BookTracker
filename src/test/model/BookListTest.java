package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookListTest {
    BookList booklist;
    Book book1;
    Book book2;
    Book book3;

    @BeforeEach
    public void setUp() {
        booklist = new BookList();
        book1 = new Book("title1", 0, "genre1", "author1", 0);
        book2 = new Book("title2", 0, "genre2", "author2", 0);
        book3 = new Book("title3", 0, "genre1", "author3", 0);

    }

    @Test
    public void testAddBook() {
        booklist.addBook(book1);
        assertEquals(book1 ,booklist.getBooks().get(0));
        booklist.addBook(book2);
        assertEquals(book2, booklist.getBooks().get(1));
    }

    @Test
    public void testRemoveBook() {
        booklist.addBook(book1);
        booklist.addBook(book2);
        booklist.addBook(book3);
        assertTrue(booklist.hasBook("title2"));
        booklist.removeBook(book2);
        assertFalse(booklist.hasBook("title2"));
    }

    @Test
    public void testHasBook() {
        assertFalse(booklist.hasBook("title1"));
        booklist.addBook(book1);
        assertTrue(booklist.hasBook("title1"));
    }

    @Test
    public void testGetBooksOfSameGenre() {
        booklist.addBook(book1);
        booklist.addBook(book2);
        booklist.addBook(book3);

        List<String> expectedList = new ArrayList<>();
        expectedList.add("title1");
        expectedList.add("title3");
        assertEquals(expectedList, booklist.getBooksOfSameGenre("genre1"));
    }

    @Test
    public void testGetBookOfSameGenreNoBooks() {
        List expectedList = new ArrayList<String>();
        assertEquals(expectedList, booklist.getBooksOfSameGenre("genre1"));
    }

    @Test
    public void testGetBookTitles() {
        booklist.addBook(book1);
        booklist.addBook(book2);
        List expectedList = new ArrayList<String>();
        expectedList.add("title1");
        expectedList.add("title2");
        assertEquals(expectedList, booklist.getBookTitles());
    }
}