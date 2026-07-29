package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class ExerciseLogRepsUpdated extends DomainEvent {

  private final ExerciseLogId logId;
  private final ExerciseLogReps reps;

  public ExerciseLogRepsUpdated(SessionId sessionId, ExerciseLogId logId, ExerciseLogReps reps) {
    super(sessionId, SessionEventType.EXERCISE_LOG_REPS_UPDATED.getMessage());
    this.logId = logId;
    this.reps = reps;
  }

  public ExerciseLogId logId() {
    return logId;
  }

  public ExerciseLogReps reps() {
    return reps;
  }
}
