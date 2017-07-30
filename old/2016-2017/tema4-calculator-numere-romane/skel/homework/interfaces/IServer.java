package homework.interfaces;

import java.util.List;

/**
 * Abstract way to handle the server.
 *
 * @author Laurentiu Stamate
 */
public interface IServer {
  /**
   * Checks whether the tokenized command
   * can be published (resolved).
   *
   * @param tokens The command split in tokens
   * @return The publishing status
   */
  boolean canPublish(String[] tokens);

  /**
   * Publishes (resolves) the command
   * and writes the results.
   *
   * @param command The command to publish
   */
  void publish(String command);

  /**
   * Subscribes (stores) the operator internally.
   *
   * @param operator The operator which is subscribed
   */
  void subscribe(String operator);

  /**
   * Gets the list with results.
   *
   * @return The list with the results
   */
  List<String> getResults();
}
