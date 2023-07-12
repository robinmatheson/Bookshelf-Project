package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

// represents all the books that are added to the user's bookshelf
public class Bookshelf implements Writable {

    private String name;
    private HashMap<String, Book> books; // key = title, value = book
    private int goal;

    // EFFECTS: constructs a bookshelf with given name and owner's name
    public Bookshelf(String name) {
        this.name = name;
        this.goal = 0;
        this.books = new HashMap<>();
        EventLog.getInstance().logEvent(new Event("Made bookshelf called " + getName()));
    }

    // REQUIRES: goal >= 0
    // MODIFIES: this
    // EFFECTS: sets goal to int given
    public void setGoal(int goal) {
        this.goal = goal;
        EventLog.getInstance().logEvent(new Event("Set reading goal to " + goal + " books."));
    }

    // MODIFIES: this
    // EFFECTS: changes bookshelf name to one given
    public void setName(String newName) {
        this.name = newName;
        EventLog.getInstance().logEvent(new Event("Changed name of bookshelf to " + getName()));
    }

    // MODIFIES: this
    // EFFECTS: adds book to list of books in the bookshelf
    public void shelveBook(Book book) {
        books.put(book.getTitle(), book);
        EventLog.getInstance().logEvent(new Event("Added " + book.getTitle() + " to bookshelf"));
    }

    // EFFECTS: returns true if the book is in the bookshelf
    public boolean inBookshelf(String title) {
        return books.containsKey(title);
    }

    // MODIFIES: this
    // EFFECTS: removes book from the bookshelf
    public void burnBook(String title) {
        books.remove(title);
        EventLog.getInstance().logEvent(new Event("Removed " + title + " from bookshelf"));
    }

    // getters

    // REQUIRES: book is already in bookshelf
    // EFFECTS: returns given book in bookshelf
    public Book getBook(String title) {
        return books.get(title);
    }

    // EFFECTS: returns list of titles of all the books in the bookshelf
    public Collection<Book> getAllBooks() {
        return books.values();
    }

    // EFFECTS: returns iterator for the HashMap of books in this bookshelf
    public Iterator<Book> getBooksIterator() {
        return books.values().iterator();
    }

    // EFFECTS: returns a string with the number of books read out of the goal number read
    public String getGoalProgress() {
        int read = getNumberRead();
        int goal = getGoal();
        if (read == 1) {
            return "You have read 1 book out of your goal of " + goal + "!";
        } else {
            return "You have read " + read + " books out of your goal of " + goal + "!";
        }
    }

    // EFFECTS: returns number of books on the bookshelf
    public int getCardinality() {
        return books.size();
    }

    // EFFECTS: returns the number of books of status READ on the bookshelf
    public int getNumberRead() {
        int count = 0;
        Iterator<Book> booksIterator = getBooksIterator();
        while (booksIterator.hasNext()) {
            if (booksIterator.next().getStatus() == BookStatus.READ) {
                count++;
            }
        }
        return count;
}

    // EFFECTS: returns owner's goal for number of books they want to read
    public int getGoal() {
        return this.goal;
    }

    // EFFECTS: returns a string stating the name of the bookshelf
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the list of books in the bookshelf
    public HashMap<String, Book> getBooks() {
        return this.books;
    }

    // source for all following methods: JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("books", booksToJson());
        json.put("goal", goal);
        return json;
    }
    
    // EFFECTS: return books in this workroom as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        Iterator<Book> booksIterator = getBooksIterator();
        while (booksIterator.hasNext()) {
            jsonArray.put(booksIterator.next().toJson());
        }
        return jsonArray;
    }

}
