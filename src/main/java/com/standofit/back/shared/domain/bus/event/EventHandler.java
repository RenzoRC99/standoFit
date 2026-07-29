package com.standofit.back.shared.domain.bus.event;

public interface EventHandler<T> {
  Class<T> eventType();

  void handle(T event);
}
