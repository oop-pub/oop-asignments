package homework.interfaces.operands;

import homework.interfaces.IToken;

/**
 * Interface used to specify that
 * a certain token is an operand.
 *
 * @author Laurentiu Stamate
 */
public interface IOperand<T extends Number> extends IToken {
  /**
   * Gets the symbol value.
   *
   * @return The stored symbol value
   */
  T getSymbolValue();

  /**
   * Sets the symbol value.
   *
   * @param value The symbol value
   */
  void setSymbolValue(T value);
}
