package checker.fileIO;

import checker.interfaces.IWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class FileWriter implements IWriter {
  private Path path;

  public FileWriter(final String filePath) throws FileNotFoundException {
    this.path = Paths.get(filePath);

    if (!Files.isRegularFile(this.path)) {
      throw new FileNotFoundException("The file was not found !");
    }
  }

  @Override
  public void write(final List<String> lines) throws IOException {
    String content = "";

    for (String s : lines) {
      content += s + '\n';
    }

    Files.write(this.path, content.getBytes());
  }
}
