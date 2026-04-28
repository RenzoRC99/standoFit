package com.standofit.back.modules.training.execution.api.error;

import java.util.UUID;

public class SessionNotFoundException extends RuntimeException {
  public SessionNotFoundException(UUID sessionId) {
    super("Session not found: " + sessionId);
  }
}
