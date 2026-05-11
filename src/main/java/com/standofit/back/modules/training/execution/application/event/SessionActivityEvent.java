package com.standofit.back.modules.training.execution.application.event;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;

public record SessionActivityEvent(
    ExecutionActivityType type, String sessionId, String status, String message, String error)
    implements ApplicationEvent {

  public static SessionActivityEvent success(
      ExecutionActivityType type, String sessionId, String message) {
    return new SessionActivityEvent(type, sessionId, "SUCCESS", message, null);
  }

  public static SessionActivityEvent failure(
      ExecutionActivityType type, String sessionId, String message, String error) {
    return new SessionActivityEvent(type, sessionId, "FAILURE", message, error);
  }
}
