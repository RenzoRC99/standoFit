package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.training.planning.domain.vo.*;

public class WorkoutExercise {
    private final WorkoutExerciseSets sets;
    private final WorkoutExerciseReps reps;
    private final WorkoutExerciseRest restSeconds;

    public WorkoutExercise(ExerciseId exerciseId, WorkoutExerciseSets sets,
                           WorkoutExerciseReps reps, WorkoutExerciseRest restSeconds) {
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
    }
}
