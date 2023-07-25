package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a book with title, author name, reading status, and star rating
public class Book implements Writable {
    private final String title;             // title of book
    private final String author;            // name of author
    private final BookStatus status;        // status is READ, CURRENTLYREADING, or TOBEREAD
    private final int rating;               // star rating for read books between 1 and 5; 0 if not yet read

    /*
     * REQUIRES: status is one of READ, CURRENTLYREADING, or TOBEREAD;
     *          star rating is between 1 and 5, or 0 if not yet read
     * EFFECTS: title of book is set to title and cannot be assigned to any other book;
     *          name of author is set to author;
     *          set status of book to one of READ, CURRENTLYREADING, or TOBEREAD;
     *          set star rating between 1 and 5, 0 if not yet read.
     */
    public Book(String title, String author, String status, int rating) {
        this.title = title;
        this.author = author;
        this.status = convertStatus(status);
        this.rating = rating;
    }

    // EFFECTS: converts string to BookStatus
    private BookStatus convertStatus(String input) {
        switch (input) {
            case "r":
            case "read":
                return BookStatus.READ;
            case "cr":
            case "currently reading":
                return BookStatus.CURRENTLYREADING;
            case "tbr":
            case "to be read":
                return BookStatus.TOBEREAD;
            default:
                return null;
        }
    }

    // getters
    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public BookStatus getStatus() {
        return this.status;
    }

    public int getRating() {
        return this.rating;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("status", status);
        json.put("rating", rating);
        return json;
    }
}

