package com.example.drivesoft.idms;

public class IDMSException extends RuntimeException {
  public IDMSException(String message) {
    super(message);
  }

  public IDMSException(String message, Throwable cause) {
    super(message, cause);
  }
}

