package checker;

import checker.fileIO.FileReader;
import checker.fileIO.FileWriter;
import checker.interfaces.IReader;
import checker.interfaces.IWriter;
import homework.interfaces.IServer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public final class Evaluator {
  private IReader subscribersReader;
  private IReader inputReader;
  private IReader solutionReader;

  private IWriter outputWriter;

  public Evaluator(final String subscribersFile,
                   final String inputFile,
                   final String outputFile,
                   final String referenceFile) throws FileNotFoundException {
    this.subscribersReader = new FileReader(subscribersFile);
    this.inputReader = new FileReader(inputFile);
    this.solutionReader = new FileReader(referenceFile);

    this.outputWriter = new FileWriter(outputFile);
  }

  public void run(final IServer server) throws IOException {
    List<String> subscribersLines = this.subscribersReader.read();
    List<String> inputLines = this.inputReader.read();
    List<String> solutionLines = this.solutionReader.read();

    for (String operator : subscribersLines) {
      server.subscribe(operator);
    }

    for (String command : inputLines) {
      server.publish(command);
    }

    List<String> outputLines = server.getResults();

    for (int i = 0; i < solutionLines.size(); i++) {
      this.compareSolution(i, solutionLines, outputLines);
    }

    this.outputWriter.write(outputLines);
  }

  private void compareSolution(final int line,
                               final List<String> solutionLines,
                               final List<String> outputLines) {
    if (solutionLines.get(line).equals(outputLines.get(line))) {
      return;
    }

    String error = "line " + line + " - " + outputLines.get(line);
    error += " <<<<< " + solutionLines.get(line);

    outputLines.set(line, error);
  }
}
