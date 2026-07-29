package com.standofit.back.shared.infrastructure.bus.event;

import com.standofit.back.shared.domain.bus.event.EventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryBus<E> {

  private static final Logger log = LoggerFactory.getLogger(InMemoryBus.class);

  private final Map<Class<?>, List<EventHandler<?>>> handlers;

  public InMemoryBus(List<? extends EventHandler<?>> handlers) {
    this.handlers = new HashMap<>();
    for (EventHandler<?> handler : handlers) {
      this.handlers.computeIfAbsent(handler.eventType(), k -> new ArrayList<>()).add(handler);
    }
  }

  public void publish(List<E> events) {
    for (E event : events) {
      publish(event);
    }
  }

  @SuppressWarnings("unchecked")
  public void publish(E event) {
    log.info("Event: {} | {}", event.getClass().getSimpleName(), event);
    var eventHandlers = handlers.get(event.getClass());
    if (eventHandlers == null) {
      log.warn("No subscriber found for event: {}", event.getClass().getSimpleName());
      return;
    }
    for (EventHandler<?> handler : eventHandlers) {
      try {
        ((EventHandler<E>) handler).handle(event);
      } catch (Exception error) {
        log.error(
            "Error handling event {}: {}",
            event.getClass().getSimpleName(),
            error.getMessage(),
            error);
      }
    }
  }
}
