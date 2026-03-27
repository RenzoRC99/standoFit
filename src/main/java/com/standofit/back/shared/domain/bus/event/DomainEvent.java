package com.standofit.back.shared.domain.bus.event;

import java.time.Instant;
import java.util.UUID;

public abstract class DomainEvent {
    private final UUID eventId;
    private final Instant ocurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.ocurredOn = Instant.now();
    }

    public abstract String  StringName();
}
