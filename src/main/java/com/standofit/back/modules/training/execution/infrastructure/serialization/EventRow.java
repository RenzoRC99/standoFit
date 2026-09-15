package com.standofit.back.modules.training.execution.infrastructure.serialization;

import java.time.Instant;
import java.util.UUID;

public record EventRow(
    UUID eventId,
    UUID aggregateId,
    String aggregateType,
    String eventName,
    String eventType,
    Instant occurredOn,
    String payload) {}
