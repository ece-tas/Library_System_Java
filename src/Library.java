import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a library that manages books, members, and borrowings.
 */
public class Library {

    private final Map<Integer, String> personDict = new HashMap<>();
    private final Map<Integer, String> bookDict = new HashMap<>();
    private List<Borrowing> borrowings = new ArrayList<>();
    private long dayPassed;

    /**
     * Constructs a Library object and initializes it with data from a file.
     *
     * @param fileName    the name of the input file
     * @param fileOutput  the name of the output file
     * @throws IOException if an I/O error occurs
     */
    public Library(String fileName, String fileOutput) throws IOException {

        FileOutput output = new FileOutput(fileOutput);
        FileInput fileInput = new FileInput();
        String[] inputFile = fileInput.readFile(fileName);
        String strDate = "";


        Time time = new Time();
        int countBook = 0;
        int countPeople = 0;

        for (String commandLine : inputFile) {
            String[] splitLine = commandLine.split("\t");
            String commandName = splitLine[0];
            if (splitLine.length == 4) {
                strDate = splitLine[3];
            }


            switch (commandName) {
                case "addBook":
                    if (countBook < 1000000) {
                        String bookType = splitLine[1];
                        countBook++;
                        Book book = new Book(bookType, countBook);
                        FileOutput.writeToFile(book.toString());

                        bookDict.put(countBook, bookType);
                    } else {
                        FileOutput.writeToFile("The limit of adding book is 999999!");
                    }
                    break;


                case "addMember":
                    if (countPeople < 1000000) {
                        String _person = splitLine[1];
                        countPeople++;
                        Person person = new Person(_person, countPeople);
                        FileOutput.writeToFile(person.toString());

                        personDict.put(countPeople, _person);
                    } else {
                        FileOutput.writeToFile("The limit of adding person is 999999!");
                    }
                    break;


                case "borrowBook":
                    int bookId = Integer.parseInt(splitLine[1]);
                    int personId = Integer.parseInt(splitLine[2]);
                    LocalDate date = time.getLocalDate(strDate);
                    LocalDate returningDate;

                    // Check if book is already borrowed
                    boolean isBookBorrowed = borrowings.stream()
                            .anyMatch(b -> b.getBookId() == bookId);
                    if (isBookBorrowed) {
                        FileOutput.writeToFile("You cannot borrow this book!");
                        break;
                    }

                    String bookTypeBorrowed = bookDict.get(bookId);
                    if (bookTypeBorrowed != null) {
                        String personType = personDict.get(personId);
                        if (personType != null) {

                            if (bookTypeBorrowed.equals("H")) {
                                FileOutput.writeToFile("You can not borrow this book!");
                            } else if (time.getLocalDate(strDate) == null) {
                                FileOutput.writeToFile("ERROR: Date format is wrong!");
                            }
                            else {
                                long countBorrowedBooks = borrowings.stream()
                                        .filter(b -> b.getPersonId() == personId)
                                        .count();
                                if (personType.equals("S") && countBorrowedBooks >= 2) {
                                    FileOutput.writeToFile("You have exceeded the borrowing limit!");
                                } else if (personType.equals("A") && countBorrowedBooks >= 4) {
                                    FileOutput.writeToFile("You have exceeded the borrowing limit!");
                                } else {

                                    Borrowing borrowing = new Borrowing(bookId, personId, date, null);

                                    long daysAdded = bookTypeBorrowed.equals("A") ? 14 : 7;
                                    returningDate = borrowing.getBorrowedDate().plusDays(daysAdded);
                                    borrowing.setReturnedDate(returningDate);

                                    borrowings.add(borrowing);
                                    FileOutput.writeToFile(borrowing.toString("borrowed", strDate));
                                }
                            }
                        } else {
                            FileOutput.writeToFile("ERROR: Person with ID " + personId + " not found!");
                        }
                    } else {
                        FileOutput.writeToFile("ERROR: Book with ID " + bookId + " not found!");
                    }
                    break;


                case "returnBook":
                    int bookIdToReturn = Integer.parseInt(splitLine[1]);
                    int personIdToReturn = Integer.parseInt(splitLine[2]);
                    LocalDate deadline = time.getLocalDate(strDate);

                    Optional<Borrowing> matchingBorrowedBook = borrowings.stream()
                            .filter(borrowing -> borrowing.getBookId() == bookIdToReturn)
                            .findFirst();

                    if (matchingBorrowedBook.isPresent()) {
                        String bookTypeReturned = bookDict.get(bookIdToReturn);

                        if (bookTypeReturned != null) {
                            String personTypeReturned = personDict.get(personIdToReturn);

                            if (personTypeReturned != null) {
                            Optional<Borrowing> returnDate = borrowings.stream()
                                    .filter(borrowing -> borrowing.getBorrowedDate() != null)
                                    .findFirst();

                                if (returnDate.isPresent()) {
                                    Borrowing borrowing = matchingBorrowedBook.get();

                                    long totalDayPassed = borrowing.isExtended() ?
                                            ChronoUnit.DAYS.between(borrowing.getReturnedDate(), deadline) :
                                            ChronoUnit.DAYS.between(borrowing.getBorrowedDate(), deadline);

                                    if (personTypeReturned.equals("A"))
                                        dayPassed = totalDayPassed > 14 ? (totalDayPassed - 7) : 0;

                                    else
                                        dayPassed = totalDayPassed > 7 ? (totalDayPassed - 7) : 0;

                                    /*if (dayPassed != 0) {
                                        FileOutput.writeToFile("You must pay a penalty!");
                                    }*/

                                    FileOutput.writeToFile(borrowing.toString("returned", strDate) + " Fee: " +
                                            dayPassed);
                                    borrowings.remove(borrowing);
                                }
                            }
                        } else {
                            FileOutput.writeToFile("ERROR: Person with ID " + personIdToReturn + " not found");
                        }
                    } else {
                        FileOutput.writeToFile("ERROR: Book with ID " + bookIdToReturn + " not found or not borrowed");
                    }
                    break;


                case "readInLibrary":
                    int bookIdToReadIn = Integer.parseInt(splitLine[1]);
                    int personIdToReadIn = Integer.parseInt(splitLine[2]);
                    LocalDate dateToReadIn = time.getLocalDate(strDate);

                    boolean isBookReadIn = borrowings.stream()
                            .anyMatch(b -> b.getBookId() == bookIdToReadIn);

                    if (isBookReadIn) {
                        FileOutput.writeToFile("You can not read this book!");
                        break;
                    }
                    String bookTypeToReadIn = bookDict.get(bookIdToReadIn);
                    if (bookTypeToReadIn != null) {
                        String personTypeToReadIn = personDict.get(personIdToReadIn);

                        if (personTypeToReadIn != null) {

                            if (personTypeToReadIn.equals("S") && bookTypeToReadIn.equals("H")) {
                                FileOutput.writeToFile("Students can not read handwritten books!");

                            } else if (time.getLocalDate(strDate) == null) {
                                FileOutput.writeToFile("ERROR: Date format is wrong!");

                            } else {
                                Borrowing borrowing = new Borrowing(bookIdToReadIn, personIdToReadIn, dateToReadIn,
                                        null);
                                borrowings.add(borrowing);
                                FileOutput.writeToFile(borrowing.toString("read in library", strDate));
                            }
                        }
                    }
                    break;


                case "extendBook":
                    int bookIdToExtend = Integer.parseInt(splitLine[1]);
                    int personIdToExtend = Integer.parseInt(splitLine[2]);
                    LocalDate currentDate = time.getLocalDate(strDate);

                    Optional<Borrowing> matchingExtendedBook = borrowings.stream()
                            .filter(borrowing -> borrowing.getBookId() == bookIdToExtend &&
                                    borrowing.getPersonId() == personIdToExtend)
                            .findFirst();

                    if (matchingExtendedBook.isPresent()) {
                        Borrowing borrowing = matchingExtendedBook.get();
                        String personTypeExtended = personDict.get(personIdToExtend);

                        if (personTypeExtended != null) {
                            if (borrowing.isExtended()) {
                                FileOutput.writeToFile("You cannot extend the deadline!");

                            } else if (currentDate != null) {
                                if (currentDate.isAfter(borrowing.getReturnedDate())) {
                                    FileOutput.writeToFile("You cannot extend the deadline for this book anymore!");

                                } else {
                                    int extensionDay = personTypeExtended.equals("S") ? 7 : 14;
                                    LocalDate newDeadline = borrowing.getReturnedDate().plusDays(extensionDay);
                                    borrowing.setReturnedDate(newDeadline);
                                    borrowing.setExtended(true);
                                    FileOutput.writeToFile(String.format("The deadline of book [%d] was extended by" +
                                                    " member [%d] at %s", bookIdToExtend, personIdToExtend, currentDate));
                                    FileOutput.writeToFile(String.format("New deadline of book [%d] is %s",
                                            bookIdToExtend, newDeadline));
                                }
                            }
                        }
                    } else {
                        FileOutput.writeToFile("ERROR: Book with ID " + bookIdToExtend + " not found or not" +
                                " borrowed by Library Member with ID " + personIdToExtend);
                    }
                    break;


                case "getTheHistory":
                    FileOutput.writeToFile("History of library:");
                    // Students
                    long countStudent = personDict.values()
                            .stream()
                            .filter(value -> value.equals("S"))
                            .count();
                    FileOutput.writeToFile(String.format("\nNumber of students: %d", countStudent));

                    for(int i = 1; i <= personDict.size(); i++) {
                        if (personDict.get(i).equals("S")) {
                            FileOutput.writeToFile(String.format("Student [id: %d]", i));
                        }
                    }
                    // Academics
                    long countAcademic = personDict.values()
                            .stream()
                            .filter(value -> value.equals("A"))
                            .count();
                    FileOutput.writeToFile("\nNumber of academics: " + countAcademic);

                    for(int i = 1; i <= personDict.size(); i++) {
                        if (personDict.get(i).equals("A")) {
                            FileOutput.writeToFile(String.format("Academic [id: %d]", i));
                        }
                    }
                    // Printed books
                    long countPrinted = bookDict.values()
                            .stream()
                            .filter(value -> value.equals("P"))
                            .count();
                    FileOutput.writeToFile("\nNumber of printed books: " + countPrinted);

                    for(int i = 1; i <= bookDict.size(); i++) {
                        if (bookDict.get(i).equals("P")) {
                            FileOutput.writeToFile(String.format("Printed [id: %d]", i));
                        }
                    }
                    // Handwritten books
                    long countHandwritten = bookDict.values()
                            .stream()
                            .filter(value -> value.equals("H"))
                            .count();
                    FileOutput.writeToFile("\nNumber of handwritten books: " + countHandwritten);

                    for(int i = 1; i <= bookDict.size(); i++) {
                        if (bookDict.get(i).equals("H")) {
                            FileOutput.writeToFile(String.format("Handwritten [id: %d]", i));
                        }
                    }
                    // Borrowed books
                    List<Borrowing> borrowedList = borrowings.stream()
                            .filter(borrowing -> borrowing.getReturnedDate() != null)
                            .collect(Collectors.toList());
                    FileOutput.writeToFile("\nNumber of borrowed books: " + borrowedList.size());

                    for (Borrowing borrowing : borrowedList) {
                        FileOutput.writeToFile(borrowing.toString
                                ("borrowed", String.valueOf(borrowing.getBorrowedDate())));
                    }
                    // Read in library books
                    List<Borrowing> readInList = borrowings.stream()
                            .filter(borrowing -> borrowing.getReturnedDate() == null)
                            .collect(Collectors.toList());
                    FileOutput.writeToFile("\nNumber of books read in library: " + readInList.size());

                    for (Borrowing readIn : readInList) {
                        FileOutput.writeToFile(readIn.toString
                                ("read in library", String.valueOf(readIn.getBorrowedDate())));
                    }
                    break;
            }
        }

        FileOutput.close();
    }

}
