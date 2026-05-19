package com.standofit.back.modules.training.execution.application.event;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import java.time.Instant;

public class SessionActivityEvent implements ApplicationEvent {

  public enum Status {
    SUCCESS,
    FAILURE
  }

  private final ExecutionActivityType activity;
  private final Status status;
  private final String entityId;
  private final String description;
  private final String errorMessage;
  private final Instant occurredOn;

  private SessionActivityEvent(
      ExecutionActivityType activity,
      Status status,
      String entityId,
      String description,
      String errorMessage) {
    this.activity = activity;
    this.status = status;
    this.entityId = entityId;
    this.description = description;
    this.errorMessage = errorMessage;
    this.occurredOn = Instant.now();
  }

  public static SessionActivityEvent success(
      ExecutionActivityType activity, String entityId, String description) {
    return new SessionActivityEvent(activity, Status.SUCCESS, entityId, description, null);
  }

  public static SessionActivityEvent failure(
      ExecutionActivityType activity, String entityId, String description, String errorMessage) {
    return new SessionActivityEvent(activity, Status.FAILURE, entityId, description, errorMessage);
  }

  public ExecutionActivityType activity() {
    return activity;
  }

  public Status status() {
    return status;
  }

  public String entityId() {
    return entityId;
  }

  public String description() {
    return description;
  }

  public String errorMessage() {
    return errorMessage;
  }

  public Instant occurredOn() {
    return occurredOn;
  }
}
