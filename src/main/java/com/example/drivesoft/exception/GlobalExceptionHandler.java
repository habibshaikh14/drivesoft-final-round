package com.example.drivesoft.exception;

import com.example.drivesoft.idms.exception.IDMSAccountListException;
import com.example.drivesoft.idms.exception.IDMSAuthenticationException;
import com.example.drivesoft.idms.exception.IDMSException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The {@code GlobalExceptionHandler} class is responsible for handling various types of exceptions
 * that occur within the application. It centralizes error handling and provides a consistent
 * response structure for different exception scenarios.
 *
 * <p>This class uses {@code @RestControllerAdvice} to globally catch exceptions thrown in the application,
 * format them into a standard error response, and return an appropriate HTTP status.</p>
 *
 * <p>Each method handles a specific set of exceptions and ensures that meaningful error messages are
 * returned to the client, along with proper HTTP status codes.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 * <li>{@code @RestControllerAdvice} - Marks this class as a global exception handler for REST APIs.</li>
 * <li>{@code @ExceptionHandler} - Handles specific exception types and maps them to appropriate responses.</li>
 * </ul>
 *
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles authentication-related exceptions such as bad credentials,
   * username not found, or general authentication errors. Returns a
   * {@code 401 Unauthorized} response with the error details.
   *
   * @param ex The exception thrown during authentication.
   * @return A {@code ResponseEntity} containing the error response and HTTP status.
   */
  @ExceptionHandler({
          AuthenticationException.class,
          BadCredentialsException.class,
          UsernameNotFoundException.class
  })
  public ResponseEntity<ErrorResponse> handleAuthenticationExceptions(RuntimeException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            "Authentication Error",
            ex.getMessage(),
            HttpStatus.UNAUTHORIZED.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles JWT-related exceptions such as expired JWT token or invalid token signature.
   * Returns a {@code 401 Unauthorized} response with an appropriate error message.
   *
   * @param ex The exception related to JWT issues.
   * @return A {@code ResponseEntity} containing the error response and HTTP status.
   */
  @ExceptionHandler({
          ExpiredJwtException.class,
          SignatureException.class
  })
  public ResponseEntity<ErrorResponse> handleJwtExceptions(RuntimeException ex) {
    String error = ex instanceof ExpiredJwtException ? "Token Expired" : "Invalid Token Signature";
    ErrorResponse errorResponse = new ErrorResponse(
            error,
            ex.getMessage(),
            HttpStatus.UNAUTHORIZED.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles authentication errors specific to the IDMS (Identity Management System).
   * Returns a {@code 401 Unauthorized} response with a detailed message.
   *
   * @param ex The exception thrown by the IDMS authentication process.
   * @return A {@code ResponseEntity} containing the error response and HTTP status.
   */
  @ExceptionHandler(IDMSAuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleIDMSAuthenticationException(IDMSAuthenticationException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            "IDMS Authentication Error",
            ex.getMessage(),
            HttpStatus.UNAUTHORIZED.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Handles exceptions related to retrieving an account list from the IDMS system.
   * Returns a {@code 500 Internal Server Error} response with the error message.
   *
   * @param ex The exception related to IDMS account list retrieval.
   * @return A {@code ResponseEntity} containing the error response and HTTP status.
   */
  @ExceptionHandler(IDMSAccountListException.class)
  public ResponseEntity<ErrorResponse> handleIDMSAccountListException(IDMSAccountListException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            "IDMS Account List Error",
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles generic IDMS-related exceptions that are not covered by more specific handlers.
   * Returns a {@code 500 Internal Server Error} response with the error details.
   *
   * @param ex The generic IDMS exception thrown.
   * @return A {@code ResponseEntity} containing the error response and HTTP status.
   */
  @ExceptionHandler(IDMSException.class)
  public ResponseEntity<ErrorResponse> handleGenericIDMSException(IDMSException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            "IDMS Error",
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles any other exceptions that are not specifically handled by other methods.
   * Returns a {@code 500 Internal Server Error} response with a generic error message.
   *
   * @param ex The generic exception thrown.
   * @return A {@code ResponseEntity} containing the error response and HTTP status.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            "Internal Server Error",
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
