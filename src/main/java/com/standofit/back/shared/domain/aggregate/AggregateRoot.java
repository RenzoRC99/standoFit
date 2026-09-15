package com.standofit.back.shared.domain.aggregate;

import com.standofit.back.shared.domain.bus.event.DomainEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

  private final List<DomainEvent> events = new ArrayList<>();

  protected void record(DomainEvent event) {
    events.add(event);
  }

  public List<DomainEvent> pullDomainEvents() {
    List<DomainEvent> result = List.copyOf(events);
    events.clear();
    return result;
  }
}
