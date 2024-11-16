package com.example.drivesoft.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code ErrorResponse} class is used to structure the error response
 * that will be returned by the API when an exception occurs.
 * This class encapsulates the error details, including the error type,
 * a message describing the issue, and the corresponding HTTP status code.
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @Data} - Lombok annotation that generates getter, setter, toString, equals, and hashCode methods.</li>
 * <li>{@code @NoArgsConstructor} - Lombok annotation that generates a no-argument constructor.</li>
 * <li>{@code @AllArgsConstructor} - Lombok annotation that generates a constructor with arguments for all fields.</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

  /**
   * The error type or code, typically a short string representing the error.
   * Example: "NOT_FOUND", "UNAUTHORIZED", etc.
   */
  private String error;

  /**
   * A detailed message providing more information about the error.
   * This message is typically provided by the exception or error handler.
   * Example: "User not found", "Invalid token", etc.
   */
  private String message;

  /**
   * The HTTP status code representing the error state.
   * Example: 404 for Not Found, 401 for Unauthorized, etc.
   */
  private int status;
}
