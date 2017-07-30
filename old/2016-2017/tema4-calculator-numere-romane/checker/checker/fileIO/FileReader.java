package checker.fileIO;

import checker.interfaces.IReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class FileReader implements IReader {
  private Path path;

  public FileReader(final String filePath) throws FileNotFoundException {
    this.path = Paths.get(filePath);

    if (!Files.isRegularFile(this.path)) {
      throw new FileNotFoundException("The file was not found !");
    }
  }

  @Override
  public List<String> read() throws IOException {
    Charset charset = Charset.forName("ISO-8859-1");

    return Files.readAllLines(this.path, charset);
  }
}
