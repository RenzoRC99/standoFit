package com.standofit.back.modules.training.execution.infrastructure;

public class SessionNotFoundException extends RuntimeException {
  public SessionNotFoundException(String id) {
    super("Session not found: " + id);
  }
}
