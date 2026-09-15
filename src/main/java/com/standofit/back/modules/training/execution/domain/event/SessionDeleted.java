package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class SessionDeleted extends DomainEvent {

  public SessionDeleted(SessionId sessionId) {
    super(sessionId, SessionEventType.SESSION_DELETED.getMessage());
  }
}
