package com.standofit.back.modules.training.execution.infrastructure.repository;

import com.standofit.back.modules.training.execution.domain.EventStore;
import com.standofit.back.modules.training.execution.domain.SessionReconstructor;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.bus.event.EventBus;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class EventSourcedSessionRepository implements SessionRepository {

  private static final String AGGREGATE_TYPE = "Session";

  private final EventStore eventStore;
  private final EventBus eventBus;

  public EventSourcedSessionRepository(EventStore eventStore, @Lazy EventBus eventBus) {
    this.eventStore = eventStore;
    this.eventBus = eventBus;
  }

  @Override
  public Session save(Session session) {
    List<DomainEvent> events = session.pullDomainEvents();
    eventStore.append(events, AGGREGATE_TYPE, session.getId().value());
    if (!events.isEmpty()) {
      eventBus.publish(events);
    }
    return session;
  }

  @Override
  public Session getById(SessionId id) {
    List<DomainEvent> events = eventStore.loadEvents(id.value());
    return SessionReconstructor.replay(events);
  }

  @Override
  public void deleteById(SessionId id) {
    throw new UnsupportedOperationException(
        "Session aggregate cannot be deleted, use cancel() or finish() instead");
  }
}
