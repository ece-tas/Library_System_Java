import java.time.LocalDate;

/**

 Represents a borrowing of a book by a library member.
 */
public class Borrowing {
    private int bookId;
    private int personId;
    private LocalDate borrowedDate;
    private boolean extended;
    private LocalDate returnedDate;

    /**

     Constructs a new Borrowing object with the specified book ID, person ID, borrowed date, and returned date.
     @param bookId the ID of the borrowed book
     @param personId the ID of the borrowing person
     @param borrowedDate the date the book was borrowed
     @param returnedDate the date the book was returned (null if not returned yet)
     */
    public Borrowing(int bookId, int personId, LocalDate borrowedDate, LocalDate returnedDate) {
        this.bookId = bookId;
        this.personId = personId;
        this.borrowedDate = borrowedDate;

        this.extended = false; // Initialize to false initially
        this.returnedDate = returnedDate;
    }
    // Getters and setters for the fields
    /**

     Checks if the borrowing is extended.
     @return true if the borrowing is extended, false otherwise
     */
    public boolean isExtended() {
        return extended;
    }

    /**

     Sets the extended status of the borrowing.
     @param extended true to mark the borrowing as extended, false otherwise
     */

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public int getBookId() {
        return bookId;
    }

    public int getPersonId() {
        return personId;
    }

    public LocalDate getReturnedDate() { return returnedDate; }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    /**

     Returns a string representation of the borrowing.
     @param text the text describing the action (e.g., "borrowed" or "returned")
     @param dte the date string
     @return a formatted string describing the borrowing action
     */
    public String toString(String text, String dte) {
        return String.format("The book [%d] was %s by member [%d] at %s", bookId, text,  personId, dte);
    }

}
