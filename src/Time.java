import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**

 A utility class for handling time-related operations.
 */
public class Time {
    private static final DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**

     Parses a string date into a LocalDate object.
     @param strDate the string representation of the date
     @return the LocalDate object representing the parsed date, or null if the parsing fails
     */
    public LocalDate getLocalDate (String strDate) {
        try {
            LocalDate date = LocalDate.parse(strDate, formattedDate);
            return date;

        } catch (DateTimeParseException e) {
            return null;
        }
    }



}
