package ui;

import model.Book;
import model.BookStatus;
import model.Bookshelf;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Vector;

import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showInputDialog;

// class for Bookshelf GUI
public class BookshelfGUI extends JFrame implements ActionListener {

    private JLabel bookshelfTitle;
    private JLabel labelDisplayGoal;
    private JLabel labelDisplayCard;

    private JTextField fieldTitle;
    private JTextField fieldAuthor;
    private JComboBox<String> statusCB;
    private JComboBox<String> ratingCB;
    private JTextField fieldBurnBook;
    private JTextField fieldSetGoal;
    private JTextField fieldChangeName;

    private DefaultTableModel dtmBooks;

    // reused for adding every component to panel
    private final GridBagConstraints c = new GridBagConstraints();

    private static final String JSON_STORE = "./data/bookshelf.json";

    private Bookshelf bs;

    // EFFECTS: calls JFrame super and method to begin building GUI
    public BookshelfGUI() {
        super("");
        initializeGUI();
    }

    // EFFECTS: opens pop-up window and instantiates bookshelf;
    //          if no input or cancel button pressed, sets name of bookshelf to default "My Bookshelf"
    private void initializeGUI() {
        String inputName = showInputDialog("Please enter the name of your bookshelf:");
        if (Objects.equals(inputName, "") || inputName == null) {
            bs = new Bookshelf("My Bookshelf");
        } else {
            bs = new Bookshelf(inputName);
        }
        mainMenu();
    }

    // MODIFIES: this
    // EFFECTS: calls all functions to set up window
    private void mainMenu() {
        setUpMainMenu();
        setUpGraphic();
        addNameTitle();
        setUpAddBook();
        setUpBurnBook();
        setUpReadingGoal();
        setUpChangeBookshelfName();
        setUpPersistence();
        setUpShelfDisplay();
        setUpCardinality();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: builds and adds frame and components
    private void setUpMainMenu() {
        setUpClosingFunctions();
        setMinimumSize(new Dimension(1300, 600));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());
    }

    // MODIFIES: this
    // EFFECTS: puts graphic in top left corner of frame
    private void setUpGraphic() {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/resources/bookshelf-transparent.png"));
        Image img = imageIcon.getImage();
        Image scaled = img.getScaledInstance(250, 65, Image.SCALE_SMOOTH);
        ImageIcon fin = new ImageIcon(scaled);
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel(fin), c);
    }

    // MODIFIES: this
    // EFFECTS: adds bookshelf name as JLabel at top of panel
    private void addNameTitle() {
        bookshelfTitle = new JLabel(bs.getName(), SwingConstants.RIGHT);
        bookshelfTitle.setFont(new Font("SansSerif", Font.BOLD, 25));
        c.gridx = 1;
        c.gridy = 0;
        add(bookshelfTitle, c);
    }

    // MODIFIES: this
    // EFFECTS: constructs labels, fields and button for adding book functionality, sets constraints and adds to fame
    private void setUpAddBook() {
        // title
        c.gridwidth = 1;
        c.insets = new Insets(40, 0, 0, 0);
        JLabel labelTitle = new JLabel("Title:");
        c.gridx = 0;
        c.gridy = 1;
        add(labelTitle, c);
        fieldTitle = new JTextField(15);
        c.gridx = 1;
        c.gridy = 1;
        add(fieldTitle, c);
        // author
        c.insets = new Insets(0, 0, 0, 0);
        JLabel labelAuthor = new JLabel("Author:");
        c.gridx = 0;
        c.gridy = 2;
        add(labelAuthor, c);
        fieldAuthor = new JTextField(15);
        c.gridx = 1;
        c.gridy = 2;
        add(fieldAuthor, c);
        // status
        JLabel labelStatus = new JLabel("Reading Status:");
        c.gridx = 0;
        c.gridy = 3;
        add(labelStatus, c);
        String[] statusChoices = new String[]{"to be read", "currently reading", "read"};
        statusCB = new JComboBox<>(statusChoices);
        statusCB.setSelectedItem("to be read");
        c.gridx = 1;
        c.gridy = 3;
        add(statusCB, c);
        // rating
        JLabel labelRating = new JLabel("Rating:");
        c.gridx = 0;
        c.gridy = 4;
        add(labelRating, c);
        String[] ratingChoices = new String[]{"0", "1", "2", "3", "4", "5"};
        ratingCB = new JComboBox<>(ratingChoices);
        ratingCB.setSelectedItem("0");
        c.gridx = 1;
        c.gridy = 4;
        add(ratingCB, c);
        // button
        JButton addBookButton = new JButton("Add Book");
        addBookButton.setActionCommand("addBookButton");
        addBookButton.addActionListener(this);
        c.gridx = 1;
        c.gridy = 5;
        add(addBookButton, c);
    }

    // MODIFIES: this
    // EFFECTS: constructs label, field and button to remove a book from the bookshelf, sets constraints and adds to
    //          frame
    private void setUpBurnBook() {
        // label
        c.insets = new Insets(30, 0, 10, 0);
        JLabel labelBurnBook = new JLabel("Enter title of the book you want to remove:");
        c.gridx = 0;
        c.gridy = 6;
        add(labelBurnBook, c);
        // text field
        fieldBurnBook = new JTextField(15);
        c.gridx = 1;
        c.gridy = 6;
        add(fieldBurnBook, c);
        // button
        JButton burnBookButton = new JButton("Burn Book");
        c.gridx = 2;
        c.gridy = 6;
        add(burnBookButton, c);
        burnBookButton.setActionCommand("burnBookButton");
        burnBookButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: constructs labels, field and button associated with creating and displaying a reading goal, sets constraints
    //          and adds to frame
    private void setUpReadingGoal() {
        // setting reading goal label
        c.insets = new Insets(0, 0, 10, 0);
        JLabel labelSetGoal = new JLabel("Enter a new reading goal:");
        c.gridx = 0;
        c.gridy = 7;
        add(labelSetGoal, c);
        // text field
        fieldSetGoal = new JTextField(8);
        c.gridx = 1;
        c.gridy = 7;
        add(fieldSetGoal, c);
        // button
        JButton setGoalButton = new JButton("Set Goal");
        c.gridx = 2;
        c.gridy = 7;
        add(setGoalButton, c);
        setGoalButton.setActionCommand("setGoalButton");
        setGoalButton.addActionListener(this);

        // displaying reading goal
        labelDisplayGoal = new JLabel("");
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0);
        add(labelDisplayGoal, c);
        updateReadingGoal();
    }

    // MODIFIES: this
    // EFFECTS: constructs label, field and button for changing the name of the bookshelf, sets constraints and
    //          adds them to frame
    private void setUpChangeBookshelfName() {
        c.gridwidth = 1;
        // label
        JLabel labelChangeName = new JLabel("Enter a new name for your bookshelf:");
        c.gridx = 0;
        c.gridy = 8;
        add(labelChangeName, c);
        // text field
        fieldChangeName = new JTextField(15);
        c.gridx = 1;
        c.gridy = 8;
        add(fieldChangeName, c);
        // button
        JButton changeNameButton = new JButton("Enter");
        c.gridx = 2;
        c.gridy = 8;
        add(changeNameButton, c);
        changeNameButton.setActionCommand("changeNameButton");
        changeNameButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: constructs labels and buttons for persistence, sets constraints and adds them to frame
    private void setUpPersistence() {
        // saving bookshelf to file
        JButton saveBookshelfButton = new JButton("Save Bookshelf");
        c.gridx = 0;
        c.gridy = 9;
        add(saveBookshelfButton, c);
        saveBookshelfButton.setActionCommand("saveBookshelf");
        saveBookshelfButton.addActionListener(this);
        // loading bookshelf from file
        JButton loadBookshelfButton = new JButton("Load Bookshelf");
        c.gridx = 1;
        c.gridy = 9;
        add(loadBookshelfButton, c);
        loadBookshelfButton.setActionCommand("loadBookshelf");
        loadBookshelfButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: constructs tables and scroller for viewing list of books in the bookshelf, sets constraints and adds
    //          to frame
    private void setUpShelfDisplay() {
        String[] colNames = {"Title", "Author", "Status", "Rating"};
        dtmBooks = new DefaultTableModel(colNames, 1)
        {
            public Class getColumnClass(int column) {
                if (column == 3) {
                    return Icon.class;
                } else {
                    return super.getColumnClass(column);
                }
            }
        };
        JTable jTableBooks = new JTable(dtmBooks);
        JScrollPane scroller = new JScrollPane(jTableBooks);
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 9;
        add(scroller, c);
    }

    // MODIFIES: this
    // EFFECTS: constructs label for the number of books shelved, sets constraints and adds to frame
    public void setUpCardinality() {
        labelDisplayCard = new JLabel("");
        c.gridx = 4;
        c.gridy = 11;
        c.anchor = GridBagConstraints.CENTER;
        add(labelDisplayCard, c);
        updateCardinality();
    }

    // MODIFIES: this, Bookshelf, Book
    // EFFECTS: listens for actions performed and executes methods when detected
    @Override
    public void actionPerformed(ActionEvent e) {
        // adding a book
        // REQUIRES: title of book does not match one already in bookshelf
        // MODIFIES: this, Bookshelf
        if (e.getActionCommand().equals("addBookButton")) {
            String title = fieldTitle.getText();
            String author = fieldAuthor.getText();
            String status = statusCB.getSelectedItem().toString();
            int rating = parseInt(ratingCB.getSelectedItem().toString());
            Book book = new Book(title, author, status, rating);
            bs.shelveBook(book);
            addBookToBooksInfo(book);
            if (status.equals("read")) {
                updateReadingGoal();
            }
            updateCardinality();
        }

        // removing a book
        // MODIFIES: this, Bookshelf
        if (e.getActionCommand().equals("burnBookButton")) {
            String titleToBurn = fieldBurnBook.getText();
            removeBookFromBooksInfo(titleToBurn);
            if (bs.getBook(titleToBurn).getStatus() == BookStatus.READ) {
                updateReadingGoal();
            }
            bs.burnBook(titleToBurn);
            updateReadingGoal();
            updateCardinality();
        }

        // setting a reading goal
        // REQUIRES: goal is an integer >= 0, or -1 to indicate no goal
        // MODIFIES: this, Bookshelf
        if (e.getActionCommand().equals("setGoalButton")) {
            bs.setGoal(parseInt(fieldSetGoal.getText()));
            updateReadingGoal();
        }

        // changing the name of the bookshelf
        // MODIFIES: this, Bookshelf
        if (e.getActionCommand().equals("changeNameButton")) {
            bs.setName(fieldChangeName.getText());
            bookshelfTitle.setText(fieldChangeName.getText());
        }

        // saving bookshelf to file
        if (e.getActionCommand().equals("saveBookshelf")) {
            try {
                JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
                jsonWriter.open();
                jsonWriter.write(bs);
                jsonWriter.close();
            } catch (FileNotFoundException exc) {
                //
            }
        }

        // loading bookshelf from file
        // MODIFIES: this, Bookshelf
        if (e.getActionCommand().equals("loadBookshelf")) {
            try {
                JsonReader jsonReader = new JsonReader(JSON_STORE);
                bs = jsonReader.read();
                bookshelfTitle.setText(bs.getName());
                loadedShelfAddBooks();
                updateCardinality();
                updateReadingGoal();
            } catch (IOException exc) {
                //
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all books from table and loads new books in from file
    private void loadedShelfAddBooks() {
        // remove books from previous bookshelf
        for (int i = 0; i < dtmBooks.getRowCount(); i++) {
            dtmBooks.removeRow(i);
        }
        // add in all new books from file
        Collection<Book> books = bs.getAllBooks();
        for (Book b : books) {
            addBookToBooksInfo(b);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates display of number of books on bookshelf
    private void updateCardinality() {
        labelDisplayCard.setText("Number of books shelved: " + bs.getCardinality());
    }

    // MODIFIES: this
    // EFFECTS: updates display of reading goal
    private void updateReadingGoal() {
        if (bs.getGoal() == -1) {
            labelDisplayGoal.setText("No reading goal set.");
        } else {
            labelDisplayGoal.setText(bs.getGoalProgress());
        }
    }

    // MODIFIES: this
    // EFFECTS: removes info of deleted book from dtmBooks
    private void removeBookFromBooksInfo(String title) {
        // find index of row to be deleted
        int idx = -1;
        Vector<Vector> vec = dtmBooks.getDataVector();
        for (int i = 1; i < dtmBooks.getRowCount(); i++) {
            Vector row = vec.elementAt(i);
            Object obj = row.elementAt(0);
            String ttl = obj.toString();
            if (ttl.equals(title)) {
                idx = i;
                break;
            }
        }
        // deletes row and updates display of table if book with title is in table
        if (idx != -1) {
            dtmBooks.removeRow(idx);
            dtmBooks.fireTableDataChanged();
        }
    }

    // REQUIRES: book must have title different from those already in the bookshelf
    // MODIFIES: this
    // EFFECTS: adds info of added book to dtmBooks
    private void addBookToBooksInfo(Book b) {
        Object[] bookData = {b.getTitle(), b.getAuthor(), statusToNiceString(b.getStatus()),
                selectRatingImage(b.getRating())};
        dtmBooks.addRow(bookData);
    }

    // REQUIRES: rating is a valid rating (guaranteed due to JComboBox)
    // EFFECTS: converts rating to an ImageIcon of stars and returns it
    private ImageIcon selectRatingImage(int rating) {
        if (rating == 0) {
            // empty image if no rating
            return new ImageIcon();
        } else {
            ImageIcon imageIcon = null;
            switch (rating) {
                case 1:
                    imageIcon = new ImageIcon(getClass().getResource("/resources/1-star-transparent.png"));
                    break;
                case 2:
                    imageIcon = new ImageIcon(getClass().getResource("/resources/2-stars-transparent.png"));
                    break;
                case 3:
                    imageIcon = new ImageIcon(getClass().getResource("/resources/3-stars-transparent.png"));
                    break;
                case 4:
                    imageIcon = new ImageIcon(getClass().getResource("/resources/4-stars-transparent.png"));
                    break;
                case 5:
                    imageIcon = new ImageIcon(getClass().getResource("/resources/5-stars-transparent.png"));
                    break;
            }
            Image img = imageIcon.getImage();
            Image scaled = img.getScaledInstance(70, 13, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
    }

    // REQUIRES: status is a valid BookStatus (guaranteed due to JComboBox)
    // EFFECTS: converts BookStatus to string to display
    private String statusToNiceString(BookStatus status) {
        if (status == BookStatus.READ) {
            return "read";
        } else if (status == BookStatus.TOBEREAD) {
            return "to be read";
        } else {
            return "currently reading";
        }
    }

    // EFFECTS: calls printLog() function upon window closing
    public void setUpClosingFunctions() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                printLog();
            }
        });
    }

    // EFFECTS: prints event log to console
    private void printLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString() + "\n");
        }
        // disposes of window and terminates JVM
        dispose();
        System.exit(0);
    }

}

