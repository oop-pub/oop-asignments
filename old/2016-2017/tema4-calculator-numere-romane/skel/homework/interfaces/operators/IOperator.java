package homework.interfaces.operators;

import homework.interfaces.IToken;

/**
 * Interface used to specify that
 * a certain token is an operator.
 *
 * @author Laurentiu Stamate
 */
public interface IOperator extends IToken {
  /**
   * Gets the priority of the operator.
   *
   * @return The priority of the operator
   */
  int getPriority();
}
