package ui;

import model.Book;
import model.BookList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

// SOURCE: https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html
// Book List GUI application
public class BookListAppUI implements ActionListener {
    private BookList readBooks;
    private BookList favoriteBooks;

    private static final String JSON_STORE = "./data/booklists.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    private JButton finishedBooks;
    private JButton favoriteBooksB;
    private JButton searchByGenre;
    private JButton saveBooks;
    private JButton loadBooks;
    private JButton exitApp;

    private final JFrame frame;
    CardLayout c1 = new CardLayout();
    JPanel contentPane = new JPanel(c1);
    JPanel finishedBooksPane;
    JPanel favoriteBooksPane;

    JPanel favoriteBooksDisplayPane;
    JPanel finishedBooksDisplayPane;

    JTextPane finishedBooksBox;
    JTextPane favoriteBooksBox;

    // EFFECTS: launches the BookList GUI app
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public BookListAppUI() {

        readBooks = new BookList();
        favoriteBooks = new BookList();
        GridLayout layout = new GridLayout(1,2);
        layout.setHgap(30);
        favoriteBooksDisplayPane = new JPanel(layout);
        finishedBooksDisplayPane = new JPanel(layout);

        // SOURCE: https://www.tutorialspoint.com/how-to-set-font-face-style-size-and-color-for-jtextpane-text-in-java
        Font font = new Font("Times New Roman", Font.PLAIN, 15);

        // SOURCE: https://stackoverflow.com/questions/3213045/centering-text-in-a-jtextarea-or-jtextpane-horizontal-text-alignment
        finishedBooksBox = new JTextPane();
        finishedBooksBox.setFont(font);
        StyledDocument documentStyle = finishedBooksBox.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        finishedBooksBox.setEditable(false);

        favoriteBooksBox = new JTextPane();
        favoriteBooksBox.setFont(font);
        StyledDocument documentStyle1 = favoriteBooksBox.getStyledDocument();
        SimpleAttributeSet centerAttribute1 = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute1, StyleConstants.ALIGN_CENTER);
        documentStyle1.setParagraphAttributes(0, documentStyle1.getLength(), centerAttribute1, false);
        favoriteBooksBox.setEditable(false);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        frame = new JFrame();

        ImageIcon image = new ImageIcon("Song inc.png");
        // SOURCE: https://www.baeldung.com/java-resize-image
        ImageIcon scaledImage = new ImageIcon(image.getImage()
                .getScaledInstance(image.getIconWidth() / 4,
                        image.getIconHeight() / 4, Image.SCALE_SMOOTH));
        JLabel companyLogo = new JLabel(scaledImage);

        JPanel mainMenuPane = new JPanel(new GridLayout(0, 1));
        mainMenuPane.setBorder(new EmptyBorder(32, 32, 30, 32));
        mainMenuPane.add(companyLogo);
        mainMenuPane.add(new JLabel("Welcome to the BookListApp, fellow bookworm. Start storing books immediately!",
                JLabel.CENTER));
        addMainMenuButtons(mainMenuPane);

        finishedBooksPane = new JPanel(new GridLayout(0,1));
        finishedBooksPane.setName("Finished Books");
        finishedBooksPane.setBorder(new EmptyBorder(20, 32, 0, 32));
        finishedBooksPane.add(new JLabel("What would you like to do with your finished books?",
                JLabel.CENTER));
        addSubMenuButtons(finishedBooksDisplayPane);
        finishedBooksDisplayPane.add(finishedBooksBox);
        finishedBooksPane.add(finishedBooksDisplayPane);

        favoriteBooksPane = new JPanel(new GridLayout(0,1));
        favoriteBooksPane.setName("Favorite Books");
        favoriteBooksPane.setBorder(new EmptyBorder(32, 32, 0, 32));
        favoriteBooksPane.add(new JLabel("What would you like to do with your favorite books?",
                JLabel.CENTER));
        addSubMenuButtons(favoriteBooksDisplayPane);
        favoriteBooksDisplayPane.add(favoriteBooksBox);
        favoriteBooksPane.add(favoriteBooksDisplayPane);

        // SOURCE: https://docs.oracle.com/javase/7/docs/api/java/awt/CardLayout.html#:~:text=A%20CardLayout%20object%20is%20a,the%20container%20is%20first%20displayed.
        contentPane.add(mainMenuPane, "Main Menu");
        contentPane.add(finishedBooksPane, "Finished Books");
        contentPane.add(favoriteBooksPane, "Favorite Books");
        c1.show(contentPane, "Main Menu");

        frame.add(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 1000);
        frame.setLocationRelativeTo(null);
        frame.setTitle("BookList Application");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                printLog(EventLog.getInstance());
            }
        });
        frame.setVisible(true);

    }

    // MODIFIES: this, c
    // EFFECTS: adds main menu buttons to the given container
    public void addMainMenuButtons(Container container) {
        finishedBooks = new JButton("Finished books");
        favoriteBooksB = new JButton("Favorite books");
        searchByGenre = new JButton("Search by genre");
        saveBooks = new JButton("Save all your books!");
        loadBooks = new JButton("Load your BookList Application");
        exitApp = new JButton("Exit application");

        container.add(finishedBooks);
        container.add(favoriteBooksB);
        container.add(searchByGenre);
        container.add(saveBooks);
        container.add(loadBooks);
        container.add(exitApp);

        finishedBooks.addActionListener(this);
        favoriteBooksB.addActionListener(this);
        searchByGenre.addActionListener(this);
        saveBooks.addActionListener(this);
        loadBooks.addActionListener(this);
        exitApp.addActionListener(this);

    }

    // MODIFIES: this, c
    // EFFECTS: adds the submenu buttons to the given container
    //          also adds the actions
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void addSubMenuButtons(Container c) {
        JPanel buttonContainer = new JPanel(new GridLayout(0,1));
        JButton addBooks = new JButton("Add a book");
        JButton removeBooks = new JButton("Remove a book");
        JButton showBookContent = new JButton("Show book details");
        JButton goBack = new JButton("Go back to previous menu");

        buttonContainer.add(addBooks);
        buttonContainer.add(removeBooks);
        buttonContainer.add(showBookContent);
        buttonContainer.add(goBack);

        c.add(buttonContainer);


        addBooks.addActionListener(e -> {
            if (finishedBooksPane.getName().equals(getVisibleCard())) {
                String title = JOptionPane.showInputDialog(frame,
                        "What is the title of this book?", null);
                int year = Integer.parseInt(JOptionPane.showInputDialog(frame,
                        "Year released?", null));
                String genre = JOptionPane.showInputDialog(frame,
                        "Genre?", null);
                String author = JOptionPane.showInputDialog(frame,
                        "Author?", null);
                int rating = Integer.parseInt(JOptionPane.showInputDialog(frame,
                        "Your rating of this book?", null));
                Book b = new Book(title, year, genre, author, rating);
                if (Stream.of(title, year, genre, author, rating).allMatch(Objects::isNull)) {
                    JOptionPane.showMessageDialog(frame, "Input not valid!", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
                readBooks.addBook(b);
                try {
                    appendString(b.getTitle() + "\n", finishedBooksBox);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                ImageIcon addingBooks = new ImageIcon("book gif.gif");
                JOptionPane.showMessageDialog(frame, "Book successfully added!", "Message",
                        JOptionPane.INFORMATION_MESSAGE, addingBooks);
            } else if (favoriteBooksPane.getName().equals(getVisibleCard())) {
                String title = JOptionPane.showInputDialog(frame,
                        "What is the title of the finished book you want to favorite?", null);
                if (!readBooks.hasBook(title)) {
                    JOptionPane.showMessageDialog(frame,
                            " ERROR: There is no such book in your finished books");
                } else {
                    for (Book b : readBooks.getBooks()) {
                        if (b.getTitle().equals(title)) {
                            favoriteBooks.addBook(b);
                            try {
                                appendString(b.getTitle() + "\n", favoriteBooksBox);
                            } catch (BadLocationException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    ImageIcon star = new ImageIcon("star.png");
                    ImageIcon scaledStar = new ImageIcon(star.getImage()
                            .getScaledInstance(star.getIconWidth() / 4,
                                    star.getIconHeight() / 4, Image.SCALE_SMOOTH));
                    JOptionPane.showMessageDialog(frame, "Book successfully favorited!",
                            "Woohooo!!!!",
                            JOptionPane.INFORMATION_MESSAGE, scaledStar);
                }

            }
        });
        removeBooks.addActionListener(e -> {
            if (finishedBooksPane.getName().equals(getVisibleCard())) {

                boolean hasBook = false;
                Book book = new Book();

                String title = JOptionPane.showInputDialog(frame,
                        "What is the title of the finished book you want to remove?", null);
                for (Book b : readBooks.getBooks()) {
                    if (b.getTitle().equals(title)) {
                        hasBook = true;
                        book = b;
                    }
                }
                if (!hasBook) {
                    JOptionPane.showMessageDialog(frame,
                            "It seems you haven't read this book anyways!");
                } else {
                    readBooks.removeBook(book);
                    refreshList();
                    ImageIcon sadFace = new ImageIcon("sadface.png");
                    ImageIcon scaledFace = new ImageIcon(sadFace.getImage()
                            .getScaledInstance(sadFace.getIconWidth() / 4,
                                    sadFace.getIconHeight() / 4, Image.SCALE_SMOOTH));
                    JOptionPane.showMessageDialog(frame, "The book has been removed",
                            "Sorry to see the book go...",
                            JOptionPane.INFORMATION_MESSAGE, scaledFace);
                }
            } else if (favoriteBooksPane.getName().equals(getVisibleCard())) {
                boolean hasBook = false;
                Book favoriteBook = new Book();

                String title = JOptionPane.showInputDialog(frame,
                        "What is the title of the favorite book you want to remove?", null);
                for (Book b : favoriteBooks.getBooks()) {
                    if (b.getTitle().equals(title)) {
                        favoriteBook = b;
                        hasBook = true;
                    }
                }
                if (!hasBook) {
                    JOptionPane.showMessageDialog(frame,
                            "This book wasn't in your favorites anyways!");
                } else {
                    favoriteBooks.removeBook(favoriteBook);
                    refreshList();
                    ImageIcon sadFace = new ImageIcon("sadface.png");
                    ImageIcon scaledFace = new ImageIcon(sadFace.getImage()
                            .getScaledInstance(sadFace.getIconWidth() / 4,
                                    sadFace.getIconHeight() / 4, Image.SCALE_SMOOTH));
                    JOptionPane.showMessageDialog(frame, "The book has been removed from favorites",
                            "Sorry to see the book go...",
                            JOptionPane.INFORMATION_MESSAGE, scaledFace);
                }
            }
        });
        showBookContent.addActionListener(e -> {
            if (finishedBooksPane.getName().equals(getVisibleCard())) {
                boolean hasBook = false;
                Book b = new Book();
                String title = JOptionPane.showInputDialog(frame,
                        "What is the title of the finished book you want to know more about?",
                        null);
                if (title.equals("")) {
                    JOptionPane.showMessageDialog(frame, "Sorry! You didn't enter anything");
                }
                for (Book book : readBooks.getBooks()) {
                    if (book.getTitle().equals(title)) {
                        hasBook = true;
                        b = book;
                    }
                }
                if (!hasBook) {
                    JOptionPane.showMessageDialog(frame,
                            "There is no such book in your list!");
                } else {
                    JOptionPane.showMessageDialog(frame,
                            getBookInfo(b));
                }
            } else if (favoriteBooksPane.getName().equals(getVisibleCard())) {
                boolean hasBook = false;
                Book b = new Book();
                String title = JOptionPane.showInputDialog(frame,
                        "What is the title of the favorite book you want to know more about?",
                        null);
                if (title.equals("")) {
                    JOptionPane.showMessageDialog(frame, "Sorry! You didn't enter anything");
                }

                for (Book book : favoriteBooks.getBooks()) {
                    if (book.getTitle().equals(title)) {
                        b = book;
                        hasBook = true;
                    }
                }
                if (!hasBook) {
                    JOptionPane.showMessageDialog(frame,
                            "There is no such book in your favorites!");
                } else {
                    JOptionPane.showMessageDialog(frame,
                            getBookInfo(b));
                }
            }
        });

        goBack.addActionListener(e -> {
            CardLayout cl = (CardLayout) (contentPane.getLayout());
            cl.show(contentPane, "Main Menu");
        });
    }


    // SOURCE : https://stackoverflow.com/questions/20451614/how-to-append-text-in-jtextpane
    // MODIFIES: box
    // EFFECTS: appends str to the given JTextPane
    public void appendString(String str, JTextPane box) throws BadLocationException {
        StyledDocument document = (StyledDocument) box.getDocument();
        document.insertString(document.getLength(), str, null);
    }

    // SOURCE: https://stackoverflow.com/questions/15940485/formatting-the-output-of-arrays-into-a-column
    // EFFECTS: returns a table in string form that describes the given books fields and values
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public String getBookInfo(Book b) {
        String title = b.getTitle();
        String year = String.valueOf(b.getYear());
        String genre = b.getGenre();
        String author = b.getAuthor();
        String rating = String.valueOf(b.getRating());
        String[] bookInfo = new String[] {
                "Title",
                "Year",
                "Genre",
                "Author",
                "Rating",
        };
        String [] userBook = new String[] {
                title,
                year,
                genre,
                author,
                rating,
        };
        StringBuilder sb = new StringBuilder(128);
        sb.append("<html><table border='0'>");
        for (int index = 0; index < bookInfo.length; index++) {

            String name = bookInfo[index];
            String info = userBook[index];

            sb.append("<tr><td align='left'>");
            sb.append(name);
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append("</td><td align='right'>");
            sb.append(" ");
            sb.append(info);
            sb.append("</td></tr>");
        }
        sb.append("<tr><td align='center'>");
        sb.append("</td><td align='right'>");
        sb.append("</td></tr>");

        return sb.toString();
    }

    // EFFECTS: saves the workroom to file
    public void saveBookLists() {
        try {
            jsonWriter.open();
            jsonWriter.write(readBooks, favoriteBooks);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadBookLists() {
        try {
            readBooks = jsonReader.readReadBooks();
            favoriteBooks = jsonReader.readFavoriteBooks();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: returns the name of the current visible panel in the frame
    public String getVisibleCard() {
        String currentVisible = null;
        for (Component card : contentPane.getComponents()) {
            if (card.isVisible()) {
                currentVisible = card.getName();
            }
        }
        return currentVisible;
    }

    // MODIFIES: this
    // EFFECTS: refreshes the finishedBooksList and the favoriteBooksList
    public void refreshList() {
        finishedBooksBox.setText("");
        favoriteBooksBox.setText("");
        for (Book b : readBooks.getBooks()) {
            try {
                appendString(b.getTitle() + "\n", finishedBooksBox);
            } catch (BadLocationException f) {
                f.printStackTrace();
            }
        }
        for (Book b : favoriteBooks.getBooks()) {
            try {
                appendString(b.getTitle() + "\n", favoriteBooksBox);
            } catch (BadLocationException f) {
                f.printStackTrace();
            }
        }
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }

    public void clearLog() {
        EventLog.getInstance().clear();
    }


    public static void main(String[] args) {
        new BookListAppUI();
    }

    // MODIFIES: this
    // EFFECTS: assigns actions to the main menu buttons
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == finishedBooks) {
            CardLayout cl = (CardLayout) (contentPane.getLayout());
            cl.show(contentPane, "Finished Books");
        } else if (e.getSource() == favoriteBooksB) {
            CardLayout cl = (CardLayout) (contentPane.getLayout());
            cl.show(contentPane, "Favorite Books");
        } else if (e.getSource() == searchByGenre) {
            String genre = JOptionPane.showInputDialog(frame,
                    "What genre?", null);
            if (genre.equals("")) {
                JOptionPane.showMessageDialog(frame,
                        "Sorry! You did not enter a genre!");
            } else {
                List<String> genreBooks = new ArrayList<>();
                for (Book b : readBooks.getBooks()) {
                    if (b.getGenre().equals(genre)) {
                        genreBooks.add(b.getTitle());
                    }
                }

                if (genreBooks.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "There are no books of that genre in your list!");
                } else {
                    JOptionPane.showMessageDialog(frame, genreBooks);
                }
            }
        } else if (e.getSource() == saveBooks) {
            int input = JOptionPane.showConfirmDialog(null,
                    "Save File?", "Select an Option...", JOptionPane.YES_NO_OPTION);
            switch (input) {
                case 0:
                    saveBookLists();
                    JOptionPane.showMessageDialog(frame, "File is saved");
                    break;
                case 1:

            }
        } else if (e.getSource() == loadBooks) {
            int input = JOptionPane.showConfirmDialog(null,
                    "Load File?", "Select an Option...", JOptionPane.YES_NO_OPTION);
            switch (input) {
                case 0:
                    loadBookLists();
                    refreshList();
                    JOptionPane.showMessageDialog(frame, "Loaded file");
                    break;
                case 1:
            }

        } else if (e.getSource() == exitApp) {
            ImageIcon goose = new ImageIcon("goose.png");
            int input = JOptionPane.showConfirmDialog(null,
                    "Exit Application?", "Select an Option...", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, goose);
            switch (input) {
                case 0:
                    printLog(EventLog.getInstance());
                    frame.dispose();
                    clearLog();
                case 1:
            }

        }


    }
}

