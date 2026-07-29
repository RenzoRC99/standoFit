package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class SessionNotesChanged extends DomainEvent {

  private final WorkoutSessionNotes notes;

  public SessionNotesChanged(SessionId sessionId, WorkoutSessionNotes notes) {
    super(sessionId, SessionEventType.SESSION_NOTES_CHANGED.getMessage());
    this.notes = notes;
  }

  public WorkoutSessionNotes notes() {
    return notes;
  }
}
