package homework.interfaces.operators.binaryOperators;


import homework.interfaces.operands.IOperand;
import homework.interfaces.operators.IOperator;

/**
 * Interface used to specify that
 * a certain operator is a binary operator.
 *
 * @author Laurentiu Stamate
 */
public interface IBinaryOperator<T extends Number> extends IOperator {
  /**
   * Calculates the value using the
   * specified operands (numbers).
   *
   * @param leftOperand  The left operand (number)
   * @param rightOperand The right operand (number)
   * @return An instance that inherits IOperand
   */
  IOperand<T> calculate(T leftOperand, T rightOperand);
}
