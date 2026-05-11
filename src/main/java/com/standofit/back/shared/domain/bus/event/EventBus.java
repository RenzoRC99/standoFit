package com.standofit.back.shared.domain.bus.event;

import java.util.List;

public interface EventBus {
  void publish(List<DomainEvent> events);

  void publish(DomainEvent event);
}
