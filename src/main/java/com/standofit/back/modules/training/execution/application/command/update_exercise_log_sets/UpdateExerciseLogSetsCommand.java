package com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;

public record UpdateExerciseLogSetsCommand(
    SessionId sessionId, ExerciseLogId logId, ExerciseLogSets sets) implements Command<Void>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return SessionActivityEvent.success(
        ExecutionActivityType.SESSION_EXERCISE_SETS_UPDATED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_EXERCISE_SETS_UPDATED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return SessionActivityEvent.failure(
        ExecutionActivityType.SESSION_EXERCISE_SETS_UPDATED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_EXERCISE_SETS_UPDATED.getDefaultDescription(),
        errorDetail);
  }
}
