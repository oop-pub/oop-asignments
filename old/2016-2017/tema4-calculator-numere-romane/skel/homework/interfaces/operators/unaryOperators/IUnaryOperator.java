package homework.interfaces.operators.unaryOperators;

import homework.interfaces.operands.IOperand;
import homework.interfaces.operators.IOperator;

/**
 * Interface used to specify that
 * a certain operator is an unary operator.
 *
 * @author Laurentiu Stamate
 */
public interface IUnaryOperator<T extends Number> extends IOperator {
  /**
   * Calculates the value using the
   * specified operand (number).
   *
   * @param operand The operand (number)
   * @return An instance that inherits IOperand
   */
  IOperand<T> calculate(T operand);
}
