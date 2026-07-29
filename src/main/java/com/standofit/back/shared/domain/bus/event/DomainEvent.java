package com.standofit.back.shared.domain.bus.event;

import com.standofit.back.shared.domain.valueobjects.Id;
import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {
  String StringName();

  Id aggregateId();

  UUID eventId();

  Instant occurredOn();
}
