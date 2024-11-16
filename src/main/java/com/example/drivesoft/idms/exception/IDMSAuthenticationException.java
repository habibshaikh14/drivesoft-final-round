package com.example.drivesoft.idms.exception;

/**
 * The {@code IDMSAuthenticationException} class represents an exception that is thrown when an authentication
 * error occurs during interaction with the IDMS (Identity Management System). This class extends {@code IDMSException}
 * and provides additional context specific to authentication failures.
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @NoArgsConstructor} - Lombok annotation to generate a no-argument constructor (if required).</li>
 * <li>{@code @AllArgsConstructor} - Lombok annotation to generate a constructor with arguments for all fields (if required).</li>
 * </ul>
 *
 * @since 1.0
 */
public class IDMSAuthenticationException extends IDMSException {

  /**
   * Constructs a new {@code IDMSAuthenticationException} with the specified detail message.
   *
   * @param message the detail message explaining the cause of the exception
   */
  public IDMSAuthenticationException(String message) {
    super(message);
  }

  /**
   * Constructs a new {@code IDMSAuthenticationException} with the specified detail message
   * and cause.
   *
   * @param message the detail message explaining the cause of the exception
   * @param cause the cause of the exception (a throwable that caused this exception)
   */
  public IDMSAuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
