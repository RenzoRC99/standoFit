package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class ExerciseLogSetsUpdated extends DomainEvent {

  private final ExerciseLogId logId;
  private final ExerciseLogSets sets;

  public ExerciseLogSetsUpdated(SessionId sessionId, ExerciseLogId logId, ExerciseLogSets sets) {
    super(sessionId, SessionEventType.EXERCISE_LOG_SETS_UPDATED.getMessage());
    this.logId = logId;
    this.sets = sets;
  }

  public ExerciseLogId logId() {
    return logId;
  }

  public ExerciseLogSets sets() {
    return sets;
  }
}
