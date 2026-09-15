package com.standofit.back.modules.training.execution.infrastructure.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "event_store",
    indexes = {@Index(name = "idx_event_store_aggregate", columnList = "aggregate_id")})
public class EventStoreJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "event_id", nullable = false)
  private UUID eventId;

  @Column(name = "aggregate_id", nullable = false)
  private UUID aggregateId;

  @Column(name = "aggregate_type", nullable = false)
  private String aggregateType;

  @Column(name = "event_name", nullable = false)
  private String eventName;

  @Column(name = "event_type", nullable = false)
  private String eventType;

  @Column(name = "occurred_on", nullable = false)
  private Instant occurredOn;

  @Column(name = "version", nullable = false)
  private int version;

  @Lob
  @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
  private String payload;

  public EventStoreJpaEntity() {}

  public EventStoreJpaEntity(
      UUID eventId,
      UUID aggregateId,
      String aggregateType,
      String eventName,
      String eventType,
      Instant occurredOn,
      int version,
      String payload) {
    this.eventId = eventId;
    this.aggregateId = aggregateId;
    this.aggregateType = aggregateType;
    this.eventName = eventName;
    this.eventType = eventType;
    this.occurredOn = occurredOn;
    this.version = version;
    this.payload = payload;
  }

  public Long getId() {
    return id;
  }

  public UUID getEventId() {
    return eventId;
  }

  public UUID getAggregateId() {
    return aggregateId;
  }

  public String getAggregateType() {
    return aggregateType;
  }

  public String getEventName() {
    return eventName;
  }

  public String getEventType() {
    return eventType;
  }

  public Instant getOccurredOn() {
    return occurredOn;
  }

  public int getVersion() {
    return version;
  }

  public String getPayload() {
    return payload;
  }
}
