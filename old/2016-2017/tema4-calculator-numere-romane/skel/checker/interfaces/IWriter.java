package checker.interfaces;

import java.io.IOException;
import java.util.List;

/**
 * Abstract way to write contents to a file.
 *
 * @author Laurentiu Stamate
 */
public interface IWriter {
  /**
   * Writes all the lines to the
   * file that was passed on the constructor.
   *
   * @param lines The list of lines to write
   * @throws IOException If the file was not found
   */
  void write(List<String> lines) throws IOException;
}
