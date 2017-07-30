package homework.interfaces;

/**
 * Abstract way to store a symbol from a command.
 *
 * @author Laurentiu Stamate
 */
public interface IToken {
  /**
   * Gets the symbol.
   *
   * @return The stored symbol
   */
  String getSymbol();

  /***
   * Sets the symbol.
   *
   * @param symbol A symbol from a command
   */
  void setSymbol(String symbol);
}
