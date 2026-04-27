package com.standofit.back.modules.training.execution.application.command.update_exercise_log_reps;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;

public record UpdateExerciseLogRepsCommand(
        SessionId sessionId,
        ExerciseLogId logId,
        ExerciseLogReps reps
) implements Command<Void> {
}