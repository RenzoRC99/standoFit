package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseSets;

public final class WorkoutExercise {
    private final WorkoutExerciseId id;
    private final ExerciseId exerciseId;
    private final WorkoutExerciseSets sets;
    private final WorkoutExerciseReps reps;
    private final WorkoutExerciseRest restSeconds;

    WorkoutExercise(WorkoutExerciseId id, ExerciseId exerciseId, WorkoutExerciseSets sets,
                   WorkoutExerciseReps reps, WorkoutExerciseRest restSeconds) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
    }

    public WorkoutExerciseId getId() { return id; }
    public ExerciseId getExerciseId() { return exerciseId; }
    public WorkoutExerciseSets getSets() { return sets; }
    public WorkoutExerciseReps getReps() { return reps; }
    public WorkoutExerciseRest getRestSeconds() { return restSeconds; }

    static WorkoutExercise create(ExerciseId exerciseId, WorkoutExerciseSets sets,
                                  WorkoutExerciseReps reps, WorkoutExerciseRest restSeconds) {
        return new WorkoutExerciseBuilder(exerciseId, sets, reps, restSeconds).build();
    }

    WorkoutExercise update(WorkoutExerciseSets sets, WorkoutExerciseReps reps, WorkoutExerciseRest restSeconds) {
        return new WorkoutExerciseBuilder(this)
                .withSets(sets)
                .withReps(reps)
                .withRestSeconds(restSeconds)
                .build();
    }
}
