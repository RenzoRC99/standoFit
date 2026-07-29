package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class ExerciseLogAdded extends DomainEvent {

  private final ExerciseLogId logId;

  public ExerciseLogAdded(SessionId sessionId, ExerciseLogId logId) {
    super(sessionId, SessionEventType.EXERCISE_LOG_ADDED.getMessage());
    this.logId = logId;
  }

  public ExerciseLogId logId() {
    return logId;
  }
}
