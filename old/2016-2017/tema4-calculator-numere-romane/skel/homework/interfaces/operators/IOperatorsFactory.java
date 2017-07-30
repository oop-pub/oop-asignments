package homework.interfaces.operators;

import homework.interfaces.IToken;

/**
 * Interface for the Operators Singleton Factory.
 *
 * @author Laurentiu Stamate
 */
public interface IOperatorsFactory {
  /**
   * Instantiates an operator.
   *
   * @param token The operator token as String
   * @return An instance that inherits IOperator
   */
  IOperator createOperator(String token);

  /**
   * Checks whether or not a token is an operator.
   *
   * @param token The token
   * @return The operator status
   */
  boolean isOperator(IToken token);

  /**
   * Checks whether or not a token is an unary operator.
   *
   * @param operator The operator
   * @return The unary operator status
   */
  boolean isUnaryOperator(IOperator operator);

  /**
   * Checks whether or not a token is a binary operator.
   *
   * @param operator The operator
   * @return The binary operator status
   */
  boolean isBinaryOperator(IOperator operator);
}
