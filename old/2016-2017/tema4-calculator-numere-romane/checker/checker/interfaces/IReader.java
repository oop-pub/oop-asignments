package checker.interfaces;

import java.io.IOException;
import java.util.List;

/**
 * Abstract way to read contents from a file.
 *
 * @author Laurentiu Stamate
 */
public interface IReader {
  /**
   * Reads all the lines from the
   * file that was passed on the constructor.
   *
   * @return A list of commands (lines)
   * @throws IOException If the file was not found
   */
  List<String> read() throws IOException;
}
