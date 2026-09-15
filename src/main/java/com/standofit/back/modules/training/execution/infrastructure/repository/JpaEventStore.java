package com.standofit.back.modules.training.execution.infrastructure.repository;

import com.standofit.back.modules.training.execution.domain.EventStore;
import com.standofit.back.modules.training.execution.infrastructure.entity.EventStoreJpaEntity;
import com.standofit.back.modules.training.execution.infrastructure.serialization.DomainEventSerializer;
import com.standofit.back.modules.training.execution.infrastructure.serialization.EventRow;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaEventStore implements EventStore {

  private final EventStoreJpaRepository repository;
  private final DomainEventSerializer serializer;

  public JpaEventStore(EventStoreJpaRepository repository, DomainEventSerializer serializer) {
    this.repository = repository;
    this.serializer = serializer;
  }

  @Override
  @Transactional
  public void append(List<DomainEvent> events, String aggregateType, UUID aggregateId) {
    int version =
        repository
            .findTopByAggregateIdOrderByVersionDesc(aggregateId)
            .map(EventStoreJpaEntity::getVersion)
            .orElse(0);

    List<EventStoreJpaEntity> entities = new ArrayList<>();
    for (DomainEvent event : events) {
      version++;
      EventRow row = serializer.serialize(event, aggregateType);
      entities.add(
          new EventStoreJpaEntity(
              row.eventId(),
              row.aggregateId(),
              row.aggregateType(),
              row.eventName(),
              row.eventType(),
              row.occurredOn(),
              version,
              row.payload()));
    }
    repository.saveAll(entities);
    repository.flush();
  }

  @Override
  public List<DomainEvent> loadEvents(UUID aggregateId) {
    List<EventStoreJpaEntity> entities = repository.findByAggregateIdOrderByVersionAsc(aggregateId);
    return entities.stream()
        .map(
            e ->
                serializer.deserialize(
                    new EventRow(
                        e.getEventId(),
                        e.getAggregateId(),
                        e.getAggregateType(),
                        e.getEventName(),
                        e.getEventType(),
                        e.getOccurredOn(),
                        e.getPayload())))
        .toList();
  }
}
