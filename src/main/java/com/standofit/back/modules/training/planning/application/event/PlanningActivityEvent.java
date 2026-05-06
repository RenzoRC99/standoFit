package com.standofit.back.modules.training.planning.application.event;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import java.time.Instant;

public class PlanningActivityEvent implements ApplicationEvent {

  public enum Status { SUCCESS, FAILURE }

  private final String activity;
  private final Status status;
  private final String entityId;
  private final String description;
  private final String errorMessage;
  private final Instant occurredOn;

  private PlanningActivityEvent(String activity, Status status, String entityId, String description, String errorMessage) {
    this.activity = activity;
    this.status = status;
    this.entityId = entityId;
    this.description = description;
    this.errorMessage = errorMessage;
    this.occurredOn = Instant.now();
  }

  public static PlanningActivityEvent success(String activity, String entityId, String description) {
    return new PlanningActivityEvent(activity, Status.SUCCESS, entityId, description, null);
  }

  public static PlanningActivityEvent failure(String activity, String entityId, String description, String errorMessage) {
    return new PlanningActivityEvent(activity, Status.FAILURE, entityId, description, errorMessage);
  }

  public String activity() { return activity; }
  public Status status() { return status; }
  public String entityId() { return entityId; }
  public String description() { return description; }
  public String errorMessage() { return errorMessage; }
  public Instant occurredOn() { return occurredOn; }
}
