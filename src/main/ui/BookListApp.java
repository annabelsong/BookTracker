package ui;

import model.Book;
import model.BookList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Book List application
public class BookListApp {
    private BookList readBooks;
    private BookList favoriteBooks;
    private Scanner input;
    private boolean activeList = true;

    private static final String JSON_STORE = "./data/booklists.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the book list application
    public BookListApp() {
        System.out.println("Welcome to the BookListApp, fellow bookworm. Start storing your books immediately!");
        initializeApp();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBookListApp();
    }

    // EFFECTS: displays the menu with options in application
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void runBookListApp() {
        String userInput;
        while (activeList) {
            displayStartMenu();
            userInput = input.next().toLowerCase();
            switch (userInput) {
                case ("q"):
                    System.out.println("Thanks for using the Book List App!");
                    activeList = false;
                    break;
                case ("b"):
                    runReadBooks();
                    break;
                case ("f"):
                    runFavoriteBooks();
                    break;
                case ("g"):
                    searchByGenre();
                    break;
                case ("s"):
                    saveBookLists();
                    break;
                case ("l"):
                    loadBookLists();
                    break;
                default:
                    System.out.println("Oh no! That's not a valid option, let's try again...");
                    runBookListApp();
                    break;
            }

        }
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

    // EFFECTS: displays menu for the book list for finished books
    private void runReadBooks() {
        displayReadBooksMenu();
        String userInput = input.next().toLowerCase();

        switch (userInput) {
            case ("a"):
                addReadBook();
                break;
            case ("s"):
                showReadBooks();
                break;
            case ("r"):
                removeReadBook();
                break;
            case ("q"):
                System.out.println("\nAlright, let's go back...");
                runBookListApp();
                break;
            default:
                System.out.println("That was not a valid option, let's try again...");
                runReadBooks();
                break;
        }

    }

    // EFFECTS: displays menu for the book list for favorite books
    private void runFavoriteBooks() {
        displayFavoriteBooksMenu();
        String userInput = input.next().toLowerCase();

        switch (userInput) {
            case ("a"):
                addFavoriteBook();
                break;
            case("r"):
                removeFavoriteBook();
                break;
            case ("s"):
                showFavoriteBooks();
                break;
            case ("q"):
                System.out.println("\n Alright, let's go back...");
                runBookListApp();
                break;
            default:
                System.out.println("That was not a valid option, let's try again...");
                runFavoriteBooks();
                break;
        }
    }


    // EFFECTS: searches through all finished books for given genre input, prints the list of them
    private void searchByGenre() {
        String desiredGenre;
        System.out.println("\nWhat genre would you like to search for?");
        desiredGenre = input.next();
        List<String> genreList = new ArrayList<>();
        for (Book b : readBooks.getBooks()) {
            if (b.getGenre().equals(desiredGenre)) {
                genreList.add(b.getTitle());
            }
        }
        if (genreList.size() == 0) {
            System.out.println("Oops, looks like there are no books of the " + desiredGenre + ".");
            System.out.println("Let's try again...");
            runBookListApp();
        } else {
            for (String s : genreList) {
                System.out.println(s);
            }
            System.out.println("These are all your finished books of the genre '" + desiredGenre + "'");
        }
        goBackAfter();
    }

    // EFFECTS: goes back to the main menu
    private void goBackAfter() {
        System.out.println("\nType any letter to go back");
        String userInput = input.next().toLowerCase();
        if (userInput.length() == 1) {
            System.out.println("Alright, let's go back...");
            runBookListApp();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a book to the finished booklist
    private void addReadBook() {
        Book newBook;
        String title;
        int year;
        String genre;
        String author;
        int rating;

        System.out.println("What is the title of the book you would like to add?");
        title = input.next();

        System.out.println("The year it was released? (enter 0 if unknown)");
        year = input.nextInt();

        System.out.println("The genre of the book?");
        genre = input.next();

        System.out.println("The author of the book?");
        author = input.next();

        System.out.println("What was your rating of the book from 1 to 10?");
        rating = input.nextInt();

        newBook = new Book(title, year, genre, author, rating);
        readBooks.addBook(newBook);

        System.out.println("\nThe book has been added! Congratulations on finishing a book!");
        runReadBooks();
    }

    // MODIFIES: this
    // EFFECTS: removes a read book from the finished list
    private void removeReadBook() {
        String title;
        boolean hasBook = false;

        System.out.println("Which book would you like to remove (enter the title)");
        title = input.next();
        for (Book b : readBooks.getBooks()) {
            if (b.getTitle().equals(title)) {
                readBooks.getBooks().remove(b);
                hasBook = true;
            }
        }

        if (!hasBook) {
            System.out.println("It seems you haven't read this book anyways! Let's go back...");
        } else {
            System.out.println("\nThe book has been removed :(, I hope you finish it soon!");
        }
        runReadBooks();
    }

    // EFFECTS: shows the list of all finished books
    private void showReadBooks() {
        String userInput;

        for (Book b : readBooks.getBooks()) {
            System.out.println(b.getTitle());
        }

        System.out.println("\nThese are all the books you have read!");
        System.out.println("Type any letter to go back");

        userInput = input.next().toLowerCase();

        if (userInput.length() == 1) {
            System.out.println("Alright, let's go back...");
            runReadBooks();
        }

    }




    // MODIFIES: this
    // EFFECTS: adds the user's desired book from their finished book list into their favorites list
    //          if the book does not exist in the user's finished list, it will print a message stating so.
    private void addFavoriteBook() {
        Book desiredBook;
        String desiredTitle;

        System.out.println("Which of your books would you like to favorite? (enter the title)");

        desiredBook = new Book("N/A", 0, "N/A", "N/A", 0);
        desiredTitle = input.next();
        for (Book b : readBooks.getBooks()) {
            if (b.getTitle().equals(desiredTitle)) {
                desiredBook = b;
            }
        }

        if (desiredBook.getTitle().equals("N/A")) {
            System.out.println("\nIt seems that this is a book that you have not finished! Let's go back...");
            runFavoriteBooks();
        }
        favoriteBooks.addBook(desiredBook);

        System.out.println("Your book has been favorited! You must really like it!\n");
        runFavoriteBooks();
    }

    // MODIFIES: this
    // EFFECTS: removes a book from the favorites book list
    private void removeFavoriteBook() {
        String title;
        boolean hasBook = false;

        System.out.println("\nWhich book would you like to remove from your favorites list (enter the title)");
        title = input.next();
        for (Book b : favoriteBooks.getBooks()) {
            if (b.getTitle().equals(title)) {
                favoriteBooks.getBooks().remove(b);
                hasBook = true;
            }
        }

        if (!hasBook) {
            System.out.println("It seems it's not in your favorites list anyways! Let's go back...");
        } else {
            System.out.println("\nThe book has been removed :(, I guess you don't like it anymore...");
        }
        runFavoriteBooks();
    }

    // EFFECTS: shows the list of all favorite books
    private void showFavoriteBooks() {
        String userInput;

        for (Book b : favoriteBooks.getBooks()) {
            System.out.println(b.getTitle());
        }

        System.out.println("\nThese are all of your favorite books!! :)");
        System.out.println("Type any letter to go back");

        userInput = input.next().toLowerCase();

        if (userInput.length() == 1) {
            System.out.println("Alright, let's go back...");
            runFavoriteBooks();
        }

    }

    // MODIFIES: this
    // EFFECTS: initializes the book list for books you've read, and a book list for books you've favorited.
    private void initializeApp() {
        readBooks = new BookList();
        favoriteBooks = new BookList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }


    // EFFECTS: displays the menu of options provided when user wants to modify their favorite books
    private void displayFavoriteBooksMenu() {
        System.out.println("\nWhat would you like to do next? \n");
        System.out.println("a - Favorite one of my finished books! \n");
        System.out.println("r - Remove a book from your favorites \n");
        System.out.println("s - See all your favorite books! \n");
        System.out.println("q - Go back to the previous menu \n");
    }

    // EFFECTS: displays the menu of options provided when user wants to modify their finished books
    private void displayReadBooksMenu() {
        System.out.println("\nWhat would you like to do next? \n");
        System.out.println("a - Add a finished book \n");
        System.out.println("r - Remove a book from your list \n");
        System.out.println("s - See all your finished books! \n");
        System.out.println("q - Go back to the previous menu \n");
    }

    // EFFECTS: displays the general menu of options to user
    private void displayStartMenu() {
        System.out.println("What are you going to do?");
        System.out.println("\nb - Modify your finished books");
        System.out.println("f - Modify your favorites list");
//        System.out.println("c - Change your rating \n");
        System.out.println("g - Search by genre ");
        System.out.println("s - Save your book application");
        System.out.println("l - Load your book application");
        System.out.println("q - Close application");
    }
}