package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class ExerciseLogRemoved extends DomainEvent {

  private final ExerciseLogId logId;

  public ExerciseLogRemoved(SessionId sessionId, ExerciseLogId logId) {
    super(sessionId, SessionEventType.EXERCISE_LOG_REMOVED.getMessage());
    this.logId = logId;
  }

  public ExerciseLogId logId() {
    return logId;
  }
}
