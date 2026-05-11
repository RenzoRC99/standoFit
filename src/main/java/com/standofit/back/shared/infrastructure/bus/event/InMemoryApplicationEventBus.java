package com.standofit.back.shared.infrastructure.bus.event;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryApplicationEventBus implements ApplicationEventBus {

  private static final Logger log = LoggerFactory.getLogger(InMemoryApplicationEventBus.class);

  private final Map<Class<?>, List<ApplicationEventHandler<?>>> handlers;

  public InMemoryApplicationEventBus(List<ApplicationEventHandler<?>> handlers) {
    this.handlers = new HashMap<>();
    for (ApplicationEventHandler<?> handler : handlers) {
      this.handlers.computeIfAbsent(handler.eventType(), k -> new ArrayList<>()).add(handler);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void publish(ApplicationEvent event) {
    log.info("Event: {} | {}", event.getClass().getSimpleName(), event);
    var eventHandlers = handlers.get(event.getClass());
    if (eventHandlers == null) return;
    for (ApplicationEventHandler<?> handler : eventHandlers) {
      ((ApplicationEventHandler<ApplicationEvent>) handler).handle(event);
    }
  }
}
