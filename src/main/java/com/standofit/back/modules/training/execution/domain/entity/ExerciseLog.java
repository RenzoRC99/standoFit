package com.standofit.back.modules.training.execution.domain.entity;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;

public class ExerciseLog {

    private final ExerciseLogId id;
    private final ExerciseId exerciseId;
    private final ExerciseLogSets sets;
    private final ExerciseLogReps reps;
    private final ExerciseLogWeight weight;

    private ExerciseLog(
            ExerciseLogId id,
            ExerciseId exerciseId,
            ExerciseLogSets sets,
            ExerciseLogReps reps,
            ExerciseLogWeight weight) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public static ExerciseLog create(ExerciseLogId id, ExerciseId exerciseId, ExerciseLogSets sets, ExerciseLogReps reps, ExerciseLogWeight weight) {
        return new ExerciseLog(id, exerciseId, sets, reps, weight);
    }

    ExerciseLog copy(ExerciseLogId id, ExerciseId exerciseId, ExerciseLogSets sets, ExerciseLogReps reps, ExerciseLogWeight weight) {
        return new ExerciseLog(id, exerciseId, sets, reps, weight);
    }

    ExerciseLog updateSets(ExerciseLogSets sets) {
        return copy(id, exerciseId, sets, reps, weight);
    }

    ExerciseLog updateReps(ExerciseLogReps reps) {
        return copy(id, exerciseId, sets, reps, weight);
    }

    ExerciseLog updateWeight(ExerciseLogWeight weight) {
        return copy(id, exerciseId, sets, reps, weight);
    }

    public ExerciseLogId getId() {
        return id;
    }

    public ExerciseId getExerciseId() {
        return exerciseId;
    }

    public ExerciseLogSets getSets() {
        return sets;
    }

    public ExerciseLogReps getReps() {
        return reps;
    }

    public ExerciseLogWeight getWeight() {
        return weight;
    }
}
