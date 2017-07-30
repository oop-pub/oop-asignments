package homework.interfaces.brackets;

import homework.interfaces.IToken;

/**
 * Interface for the Brackets Singleton Factory.
 *
 * @author Laurentiu Stamate
 */
public interface IBracketsFactory {
  /**
   * Instantiates a bracket.
   *
   * @param token The bracket token as String
   * @return An instance that inherits IBracket
   */
  IBracket createBracket(String token);

  /**
   * Checks whether or not a token is a bracket.
   *
   * @param token The token
   * @return The bracket status
   */
  boolean isBracket(IToken token);

  /**
   * Checks whether or not a bracket is an opened bracket.
   *
   * @param bracket The bracket
   * @return The bracket status
   */
  boolean isOpenedBracket(IBracket bracket);

  /**
   * Checks whether or not a bracket is a closed bracket.
   *
   * @param bracket The bracket
   * @return The bracket status
   */
  boolean isClosedBracket(IBracket bracket);

  /**
   * Checks whether or not two brackets are a bracket pair.
   *
   * @param openBracket  The opened bracket
   * @param closeBracket The closed bracket
   * @return The bracket pair status
   */
  boolean isBracketPair(IBracket openBracket, IBracket closeBracket);
}
