package com.standofit.back.shared.domain.bus.event;

import com.standofit.back.shared.domain.valueobjects.Id;
import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent {

  private final UUID eventId;
  private final Instant occurredOn;
  private final Id aggregateId;
  private final String eventName;

  protected DomainEvent(Id aggregateId, String eventName) {
    this.eventId = UUID.randomUUID();
    this.occurredOn = Instant.now();
    this.aggregateId = aggregateId;
    this.eventName = eventName;
  }

  public String StringName() {
    return eventName;
  }

  public Id aggregateId() {
    return aggregateId;
  }

  public UUID eventId() {
    return eventId;
  }

  public Instant occurredOn() {
    return occurredOn;
  }
}
