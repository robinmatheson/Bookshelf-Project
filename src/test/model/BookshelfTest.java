package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookshelfTest {

    private Bookshelf myBookshelf;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    private Book book5;

    @BeforeEach
    public void setUp() {
        myBookshelf = new Bookshelf("Robin's Bookshelf");
        myBookshelf.setGoal(100);
        book1 = new Book("Throne of Glass", "Sarah J. Maas", "r", 4);
        book2 = new Book("Love On the Brain", "Ali Hazelwood", "r", 5);
        book3 = new Book("The Secret History", "Donna Tartt", "tbr", 0);
        book4 = new Book("Heartstopper", "Alice Oseman", "cr", 0);
        book5 = new Book("The Idiot", "Elif Batuman", "cr", 0);
        Book book6 = new Book("The Poppy War", "R. F. Kuang", "tbr", 0);
        Book book7 = new Book("The Ice Princess", "Camilla Lackberg", "r", 3);
        Book book8 = new Book("Circe", "Madeline Miller", "r", 2);
        Book book9 = new Book("Baby Rudin", "Walter Rudin", "r", 1);
    }

    @Test
    public void testBookshelf() {
        assertEquals("Robin's Bookshelf", myBookshelf.getName());
        assertEquals(100, myBookshelf.getGoal());
        assertTrue(myBookshelf.getBooks().isEmpty());
    }

    @Test
    public void testSimpleGetters() {
        myBookshelf.shelveBook(book1);
        assertEquals(100, myBookshelf.getGoal());
        assertEquals("Robin's Bookshelf", myBookshelf.getName());
        assertEquals(1, myBookshelf.getCardinality());
        assertTrue(myBookshelf.inBookshelf("Throne of Glass"));
    }

    @Test
    public void testSetGoal() {
        myBookshelf.setGoal(47);
        assertEquals(47, myBookshelf.getGoal());
    }

    @Test
    public void testSetName() {
        myBookshelf.setName("Your Bookshelf");
        assertEquals("Your Bookshelf", myBookshelf.getName());
    }

    @Test
    public void testShelveOneBook() {
        myBookshelf.shelveBook(book1);
        assertEquals(book1, myBookshelf.getBook(book1.getTitle()));
        assertEquals(1, myBookshelf.getCardinality());
    }

    @Test
    public void testShelveMultipleBooks() {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        assertEquals(2, myBookshelf.getCardinality());
        assertTrue(myBookshelf.inBookshelf(book1.getTitle()));
        assertTrue(myBookshelf.inBookshelf(book2.getTitle()));
    }

    @Test
    public void testShelveRepeatBook() {
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book3);
        assertEquals(1, myBookshelf.getCardinality());
        assertTrue(myBookshelf.inBookshelf(book3.getTitle()));
    }

    @Test
    public void testRemoveOneBook() {
        myBookshelf.shelveBook(book3);
        assertEquals(1, myBookshelf.getCardinality());
        assertTrue(myBookshelf.inBookshelf(book3.getTitle()));
        myBookshelf.burnBook(book3.getTitle());
        assertFalse(myBookshelf.inBookshelf(book3.getTitle()));
    }

    @Test
    public void testRemoveOneBookOfMany() {
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        myBookshelf.shelveBook(book1);
        assertEquals(3, myBookshelf.getCardinality());
        assertTrue(myBookshelf.inBookshelf(book3.getTitle()));
        assertTrue(myBookshelf.inBookshelf(book4.getTitle()));
        assertTrue(myBookshelf.inBookshelf(book1.getTitle()));
        myBookshelf.burnBook(book3.getTitle());
        assertEquals(2, myBookshelf.getCardinality());
        assertFalse(myBookshelf.inBookshelf(book3.getTitle()));
        assertTrue(myBookshelf.inBookshelf(book4.getTitle()));
        assertTrue(myBookshelf.inBookshelf(book1.getTitle()));
    }

    @Test
    public void testRemoveMultipleBooks() {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book4);
        assertEquals(3, myBookshelf.getCardinality());
        assertTrue(myBookshelf.inBookshelf(book2.getTitle()));
        assertTrue(myBookshelf.inBookshelf(book4.getTitle()));
        assertTrue(myBookshelf.inBookshelf(book1.getTitle()));
        myBookshelf.burnBook(book2.getTitle());
        myBookshelf.burnBook(book1.getTitle());
        assertEquals(1, myBookshelf.getCardinality());
        assertFalse(myBookshelf.inBookshelf(book1.getTitle()));
        assertFalse(myBookshelf.inBookshelf(book2.getTitle()));
        assertTrue(myBookshelf.inBookshelf(book4.getTitle()));
    }

    @Test
    public void testRemoveBookNotOnShelf() {
        myBookshelf.shelveBook(book4);
        assertFalse(myBookshelf.inBookshelf(book1.getTitle()));
        myBookshelf.burnBook(book1.getTitle());
        assertEquals(1, myBookshelf.getCardinality());
        assertTrue(myBookshelf.inBookshelf(book4.getTitle()));
    }

    @Test
    public void testAddAndRemoveBook() {
        myBookshelf.shelveBook(book2);
        myBookshelf.burnBook(book2.getTitle());
        assertTrue(myBookshelf.getBooks().isEmpty());
    }

    @Test
    public void testInBookshelfBook() {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        assertTrue(myBookshelf.inBookshelf(book1.getTitle()));
    }

    @Test
    public void testNotInBookshelfBook() {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book4);
        assertFalse(myBookshelf.inBookshelf(book3.getTitle()));
    }

    @Test
    public void testGetAllBooks() {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertTrue(myBookshelf.getAllBooks().contains(book1));
        assertTrue(myBookshelf.getAllBooks().contains(book2));
        assertTrue(myBookshelf.getAllBooks().contains(book3));
    }

    @Test
    public void testGetCardinality() {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals(3, myBookshelf.getCardinality());
    }

    @Test
    public void testGetNumReadEmptyBookshelf() {
        assertEquals(0, myBookshelf.getNumberRead());
    }

    @Test
    public void testGetNumReadNoneOnBookshelf() {
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        assertEquals(0, myBookshelf.getNumberRead());
    }

    @Test
    public void testGetNumRead() {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals(2, myBookshelf.getNumberRead());
    }

    @Test
    public void testNoneGetNumRead() {
        myBookshelf.shelveBook(book4);
        myBookshelf.shelveBook(book5);
        myBookshelf.shelveBook(book3);
        assertEquals(0, myBookshelf.getNumberRead());
    }

    @Test
    public void testGetGoalProgressMultiple() {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book2);
        myBookshelf.shelveBook(book3);
        assertEquals("You have read 2 books out of your goal of 100!", myBookshelf.getGoalProgress());
    }

    @Test
    public void testGetGoalProgressSingle() {
        myBookshelf.shelveBook(book1);
        myBookshelf.shelveBook(book3);
        myBookshelf.shelveBook(book4);
        assertEquals("You have read 1 book out of your goal of 100!", myBookshelf.getGoalProgress());
    }


}
