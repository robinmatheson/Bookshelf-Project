package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.BookStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BookTest {

    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;

    @BeforeEach
    public void setUp() {
        book1 = new Book("Throne of Glass", "Sarah J. Maas", "r", 4);
        book2 = new Book("Love On the Brain", "Ali Hazelwood", "r", 5);
        book3 = new Book("The Secret History", "Donna Tartt", "tbr", 0);
        book4 = new Book("Heartstopper", "Alice Oseman", "cr", 0);
    }

    @Test
    public void testBook() {
        assertEquals("Throne of Glass", book1.getTitle());
        assertEquals("Ali Hazelwood", book2.getAuthor());
        assertEquals(TOBEREAD, book3.getStatus());
        assertEquals(0, book4.getRating());
    }

    @Test
    public void testGetters() {
        assertEquals("Throne of Glass", book1.getTitle());
        assertEquals("Donna Tartt", book3.getAuthor());
        assertEquals(READ, book2.getStatus());
        assertEquals(0, book3.getRating());
    }

}
