import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * This class represents the entry point of the program.
 */
public class Main {

    /**
     * The main method of the program.
     *
     * @param args Command-line arguments. Expects two file paths as arguments.
     * @throws IOException If an I/O error occurs while reading the files.
     */
    public static void main(String[] args) throws IOException {

        if (args.length == 2) {
            String file1 = args[0];
            String file2 = args[1];
            try {
                Library library = new Library(file1, file2);

            } catch (FileNotFoundException e) {
                System.out.println("ERROR: File can not found!");
                System.exit(0);
            } catch (NoSuchFileException ex) {
                System.out.println("ERROR: File can not found!");
                System.exit(0);
            }
        } else {
            System.out.println("ERROR: Less or more arguments entered!");
        }

    }

}