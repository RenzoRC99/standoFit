package com.standofit.back.modules.training.execution.domain;

import com.standofit.back.shared.domain.bus.event.DomainEvent;
import java.util.List;
import java.util.UUID;

public interface EventStore {

  void append(List<DomainEvent> events, String aggregateType, UUID aggregateId);

  List<DomainEvent> loadEvents(UUID aggregateId);
}
