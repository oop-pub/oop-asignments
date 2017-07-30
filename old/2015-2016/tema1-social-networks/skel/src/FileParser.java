import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Opens a file for reading and provides a stream of words resulting
 * from its parsing.
 * </p>
 */
public final class FileParser {

	String		filePath;
	BufferedReader	reader;

	/**
	 * <p>Constructor. Creates a new <code>FileParser</code> object.<p>
	 * 
	 * @param filePath - the path to the file to be read and parsed
	 */
	public FileParser(String filePath) {
		this.filePath	= filePath;
		this.reader	= null;
	}

	/**
	 * <p>
	 * Opens the file for reading.
	 * </p>
	 * 
	 * <p>
	 * This operation must be performed before calling any other methods
	 * on this object.
	 * </p>
	 */
	public void open() {
		if (null != reader) {
			throw new IllegalStateException("File already opened for reading");
		}

		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * Closes the file opened for reading.
	 * </p>
	 * 
	 * <p>
	 * Should be called after finishing reading.
	 * </p>
	 */
	public void close() {
		if (null != reader) {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * <p>
	 * Parses the underlying file and returns the next line.
	 *</p>
	 *
	 * @return A line as a <code>List&lt;String&gt;</code>, or <code>null</code> if the end
	 * of the file has been reached.
	 */
	public List<String> parseNextLine() {
		String line;

		try {
			line = reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (null == line) {
			return null;
		}

		return Arrays.asList(line.split(" "));
	}

}
