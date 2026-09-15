package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class ExerciseLogAdded extends DomainEvent {

  private final ExerciseLog log;

  public ExerciseLogAdded(SessionId sessionId, ExerciseLog log) {
    super(sessionId, SessionEventType.EXERCISE_LOG_ADDED.getMessage());
    this.log = log;
  }

  public ExerciseLog log() {
    return log;
  }
}
