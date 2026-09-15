package com.standofit.back.modules.training.execution.application.event;

import com.standofit.back.modules.training.execution.domain.event.SessionDeleted;
import com.standofit.back.modules.training.execution.infrastructure.query.SessionReadViewUpdater;
import com.standofit.back.shared.domain.bus.event.DomainEventHandler;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OnSessionDeleted implements DomainEventHandler<SessionDeleted> {

  private final SessionReadViewUpdater readViewUpdater;

  public OnSessionDeleted(SessionReadViewUpdater readViewUpdater) {
    this.readViewUpdater = readViewUpdater;
  }

  @Override
  public Class<SessionDeleted> eventType() {
    return SessionDeleted.class;
  }

  @Override
  @Transactional
  public void handle(SessionDeleted event) {
    readViewUpdater.remove(new SessionId(event.aggregateId().value()).value());
  }
}
