import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutput {

    private static BufferedWriter output = null;

    /**
     * Constructs a new FileOutput object with the specified file name.
     *
     * @param fileName the name of the file to write
     * @throws IOException if an I/O error occurs
     */
    public FileOutput(String fileName) throws IOException {
        output = new BufferedWriter(new FileWriter(fileName));
    }

    /**
     * Writes the specified text to the output file.
     *
     * @param text the text to write to the file
     */
    public static void writeToFile(String text) {
        try {
            output.write(text);
            output.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Closes the output file, releasing any system resources associated with it.
     *
     * @throws IOException if an I/O error occurs
     */
    public static void close() throws IOException {
        output.close();
    }
}
