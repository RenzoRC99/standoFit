package com.standofit.back.modules.training.execution.infrastructure.repository;

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
public class EventDrivenSessionRepository implements SessionRepository {

  private final JpaSessionRepository delegate;
  private final EventBus eventBus;

  public EventDrivenSessionRepository(JpaSessionRepository delegate, @Lazy EventBus eventBus) {
    this.delegate = delegate;
    this.eventBus = eventBus;
  }

  @Override
  public Session save(Session session) {
    List<DomainEvent> events = session.pullDomainEvents();
    Session saved = delegate.save(session);
    if (!events.isEmpty()) {
      eventBus.publish(events);
    }
    return saved;
  }

  @Override
  public Session getById(SessionId id) {
    return delegate.getById(id);
  }

  @Override
  public void deleteById(SessionId id) {
    delegate.deleteById(id);
  }
}
