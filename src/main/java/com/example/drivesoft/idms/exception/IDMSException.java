package com.example.drivesoft.idms.exception;

/**
 * The {@code IDMSException} class is a custom exception used to represent general errors
 * related to interactions with the IDMS (Identity Management System). It extends the {@link RuntimeException}
 * class to allow for unchecked exceptions that can be thrown during the execution of IDMS-related operations.
 *
 * <p>This exception is used as the base class for more specific exceptions like {@link IDMSAuthenticationException}
 * and {@link IDMSAccountListException}, which provide more detailed error handling for particular IDMS operations.</p>
 *
 * <p>Constructor Details:</p>
 * <ul>
 * <li>{@code IDMSException(String message)} - Creates an exception with the specified error message.</li>
 * <li>{@code IDMSException(String message, Throwable cause)} - Creates an exception with the specified error message and cause.</li>
 * </ul>
 *
 * @since 1.0
 */
public class IDMSException extends RuntimeException {

  /**
   * Constructs a new {@code IDMSException} with the specified detail message.
   *
   * @param message the detail message that describes the error
   */
  public IDMSException(String message) {
    super(message);
  }

  /**
   * Constructs a new {@code IDMSException} with the specified detail message and cause.
   *
   * @param message the detail message that describes the error
   * @param cause the cause of the error, typically an underlying exception
   */
  public IDMSException(String message, Throwable cause) {
    super(message, cause);
  }
}
