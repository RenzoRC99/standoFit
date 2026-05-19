package com.standofit.back.modules.training.execution.application.command.remove_exercise_log;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.EventfulCommand;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;

public record RemoveExerciseLogCommand(SessionId sessionId, ExerciseLogId logId)
    implements Command<Void>, EventfulCommand {

  public ApplicationEvent toSuccessEvent() {
    return SessionActivityEvent.success(
        ExecutionActivityType.SESSION_EXERCISE_REMOVED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_EXERCISE_REMOVED.getDefaultDescription());
  }

  public ApplicationEvent toFailureEvent(String errorDetail) {
    return SessionActivityEvent.failure(
        ExecutionActivityType.SESSION_EXERCISE_REMOVED,
        sessionId.value().toString(),
        ExecutionActivityType.SESSION_EXERCISE_REMOVED.getDefaultDescription(),
        errorDetail);
  }
}
