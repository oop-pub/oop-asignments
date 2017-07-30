package homework.interfaces.operands;

/**
 * Interface for the Operands Singleton Factory.
 *
 * @author Laurentiu Stamate
 */
public interface IOperandsFactory<T extends Number> {
  /**
   * Instantiates an operand.
   *
   * @param token The operand token as String
   * @return An instance that inherits IOperand
   */
  IOperand<T> createOperand(String token);

  /**
   * Converts an arab number to a roman number.
   *
   * @param arabNumber The arab number
   * @return An instance that inherits IOperand
   */
  IOperand<T> convertToRomanNumber(T arabNumber);

  /**
   * Converts a roman number to an arab number.
   *
   * @param romanNumber The roman number
   * @return An instance that inherits IOperand
   */
  IOperand<T> convertToArabNumber(String romanNumber);
}
