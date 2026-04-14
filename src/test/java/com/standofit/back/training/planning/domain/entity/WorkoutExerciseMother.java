package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.training.planning.domain.vo.*;

import java.util.UUID;

public final class WorkoutExerciseMother {

    private WorkoutExerciseMother() {}

    public static ExerciseId anExerciseId() {
        return new ExerciseId(UUID.randomUUID());
    }

    public static WorkoutExerciseSets defaultSets() {
        return new WorkoutExerciseSets(3);
    }

    public static WorkoutExerciseReps defaultReps() {
        return new WorkoutExerciseReps(10);
    }

    public static WorkoutExerciseRest defaultRest() {
        return new WorkoutExerciseRest(60);
    }

    public static WorkoutExercise aWorkoutExercise() {
        return WorkoutExercise.create(
                anExerciseId(),
                defaultSets(),
                defaultReps(),
                defaultRest()
        );
    }

    public static WorkoutExercise aWorkoutExerciseWith(int sets, int reps, int rest) {
        return WorkoutExercise.create(
                anExerciseId(),
                new WorkoutExerciseSets(sets),
                new WorkoutExerciseReps(reps),
                new WorkoutExerciseRest(rest)
        );
    }
}
