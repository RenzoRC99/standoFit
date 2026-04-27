package com.standofit.back.modules.training.execution.application.command.remove_exercise_log;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;

public record RemoveExerciseLogCommand(
        SessionId sessionId,
        ExerciseLogId logId
) implements Command<Void> {
}