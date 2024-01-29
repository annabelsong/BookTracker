# My Personal Project
## Book Tracker

My program will do the following:
- Keeps track of books that have been read by the user 
- Store each book with a title, year, genre, author, and rating (out of 10)
- Lets the user have a *favorites* list for books

As a person who has read a lot of books, I do regret not recording them as I'm sure to have forgotten some books I've 
finished reading over the years. Due to this regret, I am making a book tracker!
This program will help bookworms all over the world keep track of all the books that they have read so far and 
have information about each stored book easily. It will also allow them to store a rating with each book that they
finish reading. The program will also be able to output lists of books of the same genre.

## User Stories

- As a user, I want to be able to add the book I've just finished with my rating of it
- As a user, I want to be able to see all the books I've finished so far
- As a user, I want to put some books I've read into a *favorites* list
- As a user, I want to find out how many books of a given genre I have read.
- As a user, I want the ability to save the entire state of the application to file
- As a user, I want the ability to reload a BookList app file that I recently saved.

## Phase 4: Task 2
Fri Apr 01 16:58:30 PDT 2022
Added book to booklist : hello

Fri Apr 01 16:58:30 PDT 2022
Added book to booklist : hello1

Fri Apr 01 16:58:30 PDT 2022
Added book to booklist : bye

Fri Apr 01 16:58:30 PDT 2022
Added book to booklist : bye2

Fri Apr 01 16:58:30 PDT 2022
Added book to booklist : hello1

Fri Apr 01 16:58:30 PDT 2022
Added book to booklist : bye2

Fri Apr 01 16:58:35 PDT 2022
Removed book from booklist : hello

Fri Apr 01 16:58:38 PDT 2022
Removed book from booklist : bye


## Phase 4: Task 3

I created two UMLs, one with the BookListApp class and one without. This is because the BookListApp class
is now obsolete and serves no purpose in my code! I didn't know if I needed to include that in my UML though, so I drew both.
For the UML with the BookListApp class, I don't like how I crossed the lines (association lines intersected) for
some of the associations. However, I do think the UML is good and neat!

If I had more time to work on this project, I would definitely spend a lot of time refactoring the code
using the techniques we've learned in this course so far. There is an ungodly amount of duplication in my code 
currently, and I would love to find a way to reduce that. Most of the duplication I want to fix lies in my
BookListAppUI class where I have duplicated codes for the different button action events. If I had more time
I would also add more methods into my BookListAppUI class. Several of my methods are too long and don't pass the 
checkstyle method length due to my methods being bulky. If I implemented more methods that did more
specific things, and then called them all in a bigger function, it would look much nicer.
