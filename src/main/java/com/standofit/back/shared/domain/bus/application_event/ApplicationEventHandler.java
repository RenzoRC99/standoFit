package com.standofit.back.shared.domain.bus.application_event;

public interface ApplicationEventHandler<T extends ApplicationEvent> {
  Class<T> eventType();

  void handle(T event);
}
