# GreatReads (GUI)

*GreatReads* is an application where users can keep track of the books in their library. Users are able to add a book
with a title, reading status, and star rating to their bookshelf, where they can keep track of their books, their
progress toward their reading goal, and view different groupings of the books on their shelf. This is the edition of my 
application with a graphical user interface.

I created *GreatReads* as a way to learn Java, Java Swing, IntelliJ, GitHub, software construction, and building a graphical user interface. 
As an avid reader, I wanted to try my hand at an application where I could store my books and the main information that I am interested in
(their status and rating).

Building this project from scratch taught me a lot about the process of developing an application, such as:
- brainstorming and planning out which functionalities I wanted and if it would be feasible and reasonable with the construction of a GUI
- implementing my functionalities effectively
- debugging and writing tests
- creating a graphical UI using Java Swing that is effective, intuitive, and aesthetically pleasing
- refactoring code to use a more appropriate data structure for the bookshelf
- implementing persistence with Json
- implementing an action log that prints when the application is closed
- object-oriented programming, data abstraction, encapsulation, single responsibility principle, decreasing class
coupling, increasing class cohesion, etc.

Note that the GUI version of *GreatReads* has fewer functionalities implemented than the console UI version. Hence, there 
are also fewer tests as I have not created any to test the GUI.

### Features
First, you need to create a bookshelf with a name.
##### Then, you can:
- add a book with the title, author name, read status, and star rating to your bookshelf
- remove a book from your bookshelf
- view the books on your bookshelf
- view the total number of books you have
- set a goal for the number of books you want to read, with read books contributing to progress
- view progress in reading goal
- save your bookshelf to file
- load a bookshelf from file
- change the name of your bookshelf
- view a log of your events printed to the console upon quitting

### Future plans
- adding a prompt when first running the application to ask if the user would like to load a bookshelf from file or proceed with creating a new one
- adding a prompt when quitting to ask if the user would like to save the current bookshelf to file
- refactor implementation of the HashMap and hashcode generation to allow two books with the same title to be on the bookshelf
- redesign GUI such that it utilizes panels for separating the different interactive parts of the frame
  - i.e. all the "add book" labels, fields, combo boxes, and button would be in their own panel in a way that shows they are a collective part

### How to use
1. Clone this repo
2. Open the project in your favourite code editor with a Java compiler
4. Run 'Main'
5. A new window should open up asking for you to name your bookshelf
   <img width="508" alt="Screenshot 2023-08-09 at 1 26 26 PM" src="https://github.com/robinmatheson/GreatReads-GUI/assets/125790030/33ca4431-9655-4d45-9f4d-f623a9a9e212">
6. You can input your custom name or press "Cancel" which will create a default bookshelf named "My Bookshelf"
7. A new window will pop up where you are able to edit and view your bookshelf.
   <img width="1412" alt="Screenshot 2023-08-09 at 1 28 25 PM" src="https://github.com/robinmatheson/GreatReads-GUI/assets/125790030/5b297198-3c55-447a-8798-31eeb28546b7">
8. Have fun tracking all the books on your bookshelf (or library if you've reached 1000 books!)

   *Here is a use example:*
   
   <video width="500" src="https://github.com/robinmatheson/GreatReads-GUI/assets/125790030/ee76086d-0366-4a78-a24a-2eee18857fd2"/>


