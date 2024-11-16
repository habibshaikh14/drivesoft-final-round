package com.example.drivesoft.exception;

import com.example.drivesoft.idms.IDMSAccountListException;
import com.example.drivesoft.idms.IDMSAuthenticationException;
import com.example.drivesoft.idms.IDMSException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Handle authentication-related exceptions
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

  // Handle JWT-specific exceptions
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

  // Handle custom IDMS exceptions
  @ExceptionHandler(IDMSAuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleIDMSAuthenticationException(IDMSAuthenticationException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            "IDMS Authentication Error",
            ex.getMessage(),
            HttpStatus.UNAUTHORIZED.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(IDMSAccountListException.class)
  public ResponseEntity<ErrorResponse> handleIDMSAccountListException(IDMSAccountListException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            "IDMS Account List Error",
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IDMSException.class)
  public ResponseEntity<ErrorResponse> handleGenericIDMSException(IDMSException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            "IDMS Error",
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // Handle generic exceptions
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
