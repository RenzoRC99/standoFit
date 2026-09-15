package com.standofit.back.shared.infrastructure.bus.event;

import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.bus.event.DomainEventHandler;
import com.standofit.back.shared.domain.bus.event.EventBus;
import java.util.List;

public class InMemoryEventBus implements EventBus {

  private final InMemoryBus<DomainEvent> bus;

  public InMemoryEventBus(List<DomainEventHandler<?>> handlers) {
    this.bus = new InMemoryBus<>(handlers);
  }

  @Override
  public void publish(List<DomainEvent> events) {
    bus.publish(events);
  }

  @Override
  public void publish(DomainEvent event) {
    bus.publish(event);
  }
}
