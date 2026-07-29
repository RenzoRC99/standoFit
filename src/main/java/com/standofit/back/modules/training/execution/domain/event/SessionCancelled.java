package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class SessionCancelled extends DomainEvent {

  public SessionCancelled(SessionId sessionId) {
    super(sessionId, SessionEventType.SESSION_CANCELLED.getMessage());
  }
}
