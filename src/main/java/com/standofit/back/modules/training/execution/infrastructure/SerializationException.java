package com.standofit.back.modules.training.execution.infrastructure;

public class SerializationException extends RuntimeException {

  public SerializationException(String message) {
    super(message);
  }

  public SerializationException(String message, Throwable cause) {
    super(message, cause);
  }
}
