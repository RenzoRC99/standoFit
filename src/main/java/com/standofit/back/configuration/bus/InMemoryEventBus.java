package com.standofit.back.configuration.bus;

import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.bus.event.EventBus;
import java.util.List;

public class InMemoryEventBus implements EventBus {

  @Override
  public void publish(List<DomainEvent> events) {
    // TODO: dispatch to registered subscribers
  }

  @Override
  public void publish(DomainEvent event) {
    // TODO: dispatch to registered subscribers
  }
}
