import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileInput {

    /**
     * Reads a file and returns its contents as an array of non-empty lines.
     *
     * @param path the path of the file to read
     * @return an array of non-empty lines from the file, or null if an error occurs
     */
    static String[] readFile(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            List<String> nonEmptyLines = new ArrayList<>();

            for (String line : lines) {
                if (!line.trim().isEmpty()) { // check if line is not empty after trimming whitespace
                    nonEmptyLines.add(line);
                }
            }

            String[] results = new String[nonEmptyLines.size()];
            nonEmptyLines.toArray(results);

            return results;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}