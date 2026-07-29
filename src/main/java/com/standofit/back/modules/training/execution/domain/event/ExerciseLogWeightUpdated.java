package com.standofit.back.modules.training.execution.domain.event;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

public final class ExerciseLogWeightUpdated extends DomainEvent {

  private final ExerciseLogId logId;
  private final ExerciseLogWeight weight;

  public ExerciseLogWeightUpdated(
      SessionId sessionId, ExerciseLogId logId, ExerciseLogWeight weight) {
    super(sessionId, SessionEventType.EXERCISE_LOG_WEIGHT_UPDATED.getMessage());
    this.logId = logId;
    this.weight = weight;
  }

  public ExerciseLogId logId() {
    return logId;
  }

  public ExerciseLogWeight weight() {
    return weight;
  }
}
