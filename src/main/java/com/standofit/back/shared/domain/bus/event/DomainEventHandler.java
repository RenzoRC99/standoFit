package com.standofit.back.shared.domain.bus.event;

public interface DomainEventHandler<T extends DomainEvent> extends EventHandler<T> {}
