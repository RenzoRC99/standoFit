package com.standofit.back.modules.training.execution.application.command.add_exercise_log;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;

public record AddExerciseLogCommand(
        SessionId sessionId,
        ExerciseLogId logId,
        ExerciseId exerciseId,
        ExerciseLogSets sets,
        ExerciseLogReps reps,
        ExerciseLogWeight weight
) implements Command<Void> {
}