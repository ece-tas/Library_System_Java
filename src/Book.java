public class Book {
    private String bookType;
    private int id;

    /**

     Constructs a new Book object with the given book type and ID.
     @param bookType the type of the book (P for Printed, H for Handwritten)
     @param countBook the ID of the book
     */

    public Book(String bookType, int countBook) {
        this.bookType = bookType;
        this.id = countBook;
    }

    /**

     Returns the type of the book.
     @return the type of the book ("Printed" or "Handwritten") or "ERROR" if the book type is unknown
     */

    private String getType() {
        if (getBookType().equals("P")) {
            return "Printed";
        } else if (getBookType().equals("H")) {
            return "Handwritten";
        } else {
            return "ERROR";
        }
    }

    /**

     Returns a string representation of the book.
     @return a string representation of the book in the format "Created new book: [type] [id]"
     or "ERROR" if the book type is unknown
     */
    @Override
    public String toString() {
        String type = getType();
        if (type.equals("ERROR")) {
            return "ERROR";
        } else {
            return String.format("Created new book: %s [id: %d]", getType(), id);

        }
    }

    /**

     Returns the book type.
     @return the book type
     */
    public String getBookType() {
        return bookType;
    }

    /**

     Returns the ID of the book.
     @return the ID of the book
     */
    public int getId() {
        return id;
    }

}
