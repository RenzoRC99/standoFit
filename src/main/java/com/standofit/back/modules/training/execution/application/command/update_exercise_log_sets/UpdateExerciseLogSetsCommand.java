package com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;

public record UpdateExerciseLogSetsCommand(
        SessionId sessionId,
        ExerciseLogId logId,
        ExerciseLogSets sets
) implements Command<Void> {
}