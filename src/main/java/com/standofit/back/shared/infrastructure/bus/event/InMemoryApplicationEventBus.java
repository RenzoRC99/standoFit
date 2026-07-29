package com.standofit.back.shared.infrastructure.bus.event;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventHandler;
import java.util.List;

public class InMemoryApplicationEventBus implements ApplicationEventBus {

  private final InMemoryBus<ApplicationEvent> bus;

  public InMemoryApplicationEventBus(List<ApplicationEventHandler<?>> handlers) {
    this.bus = new InMemoryBus<>(handlers);
  }

  @Override
  public void publish(ApplicationEvent event) {
    bus.publish(event);
  }
}
