package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseSets;

import java.util.UUID;

final class WorkoutExerciseBuilder {
    private final WorkoutExerciseId id;
    private final ExerciseId exerciseId;
    private WorkoutExerciseSets sets;
    private WorkoutExerciseReps reps;
    private WorkoutExerciseRest restSeconds;

    WorkoutExerciseBuilder(ExerciseId exerciseId, WorkoutExerciseSets sets,
                          WorkoutExerciseReps reps, WorkoutExerciseRest restSeconds) {
        this.id = new WorkoutExerciseId(UUID.randomUUID());
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
    }

    WorkoutExerciseBuilder(WorkoutExercise exercise) {
        this.id = exercise.getId();
        this.exerciseId = exercise.getExerciseId();
        this.sets = exercise.getSets();
        this.reps = exercise.getReps();
        this.restSeconds = exercise.getRestSeconds();
    }

    WorkoutExerciseBuilder withSets(WorkoutExerciseSets sets) {
        this.sets = sets;
        return this;
    }

    WorkoutExerciseBuilder withReps(WorkoutExerciseReps reps) {
        this.reps = reps;
        return this;
    }

    WorkoutExerciseBuilder withRestSeconds(WorkoutExerciseRest restSeconds) {
        this.restSeconds = restSeconds;
        return this;
    }

    WorkoutExercise build() {
        return new WorkoutExercise(id, exerciseId, sets, reps, restSeconds);
    }
}
