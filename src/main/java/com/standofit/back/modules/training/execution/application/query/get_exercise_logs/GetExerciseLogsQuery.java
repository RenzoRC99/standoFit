package com.standofit.back.modules.training.execution.application.query.get_exercise_logs;

import com.standofit.back.api.execution.dto.ExerciseLogDTO;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;

import java.util.List;

public record GetExerciseLogsQuery(SessionId sessionId) implements Query<List<ExerciseLogDTO>> {
}
