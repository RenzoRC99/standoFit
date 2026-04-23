package com.standofit.back.modules.training.planning.application.query.get_workout_by_id;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;

import java.util.UUID;

public class GetWorkoutByIdQuery implements Query<WorkoutDto> {
    private final WorkoutId workoutId;

    public GetWorkoutByIdQuery(UUID workoutId) {
        this.workoutId = new WorkoutId(workoutId);
    }

    public WorkoutId getWorkoutId() {
        return workoutId;
    }
}
