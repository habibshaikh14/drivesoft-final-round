package com.example.drivesoft.idms.exception;

/**
 * The {@code IDMSAccountListException} class represents an exception that is thrown when an error
 * occurs while retrieving or processing the list of accounts from the IDMS (Identity Management System).
 * This class extends {@code IDMSException} and provides additional context or information
 * specific to the account list retrieval process.
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @NoArgsConstructor} - Lombok annotation to generate a no-argument constructor (if required).</li>
 * <li>{@code @AllArgsConstructor} - Lombok annotation to generate a constructor with arguments for all fields (if required).</li>
 * </ul>
 *
 * @since 1.0
 */
public class IDMSAccountListException extends IDMSException {

  /**
   * Constructs a new {@code IDMSAccountListException} with the specified detail message.
   *
   * @param message the detail message explaining the cause of the exception
   */
  public IDMSAccountListException(String message) {
    super(message);
  }

  /**
   * Constructs a new {@code IDMSAccountListException} with the specified detail message
   * and cause.
   *
   * @param message the detail message explaining the cause of the exception
   * @param cause   the cause of the exception (a throwable that caused this exception)
   */
  public IDMSAccountListException(String message, Throwable cause) {
    super(message, cause);
  }
}
