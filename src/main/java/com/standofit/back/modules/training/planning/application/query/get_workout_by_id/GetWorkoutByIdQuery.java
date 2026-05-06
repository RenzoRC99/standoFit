package com.standofit.back.modules.training.planning.application.query.get_workout_by_id;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;

public record GetWorkoutByIdQuery(WorkoutId workoutId) implements Query<WorkoutDto> {
}
