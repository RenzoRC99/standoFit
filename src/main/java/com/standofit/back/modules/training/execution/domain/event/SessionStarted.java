package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class SessionStarted extends DomainEvent {

  private final SessionDayId dayId;

  public SessionStarted(SessionId sessionId, SessionDayId dayId) {
    super(sessionId, SessionEventType.SESSION_STARTED.getMessage());
    this.dayId = dayId;
  }

  public SessionDayId dayId() {
    return dayId;
  }
}
