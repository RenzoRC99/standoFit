package com.standofit.back.training.planning.domain;

import com.standofit.shared.domain.ids.ExerciseId;
import com.standofit.back.training.planning.domain.vo.RoutineExerciseSetsVO;
import com.standofit.back.training.planning.domain.vo.RoutineExerciseRepsVO;

public final class RoutineExercise {
    private final ExerciseId id;
    private final RoutineExerciseSetsVO targetSets;
    private final RoutineExerciseRepsVO targetReps;

    public RoutineExercise(ExerciseId id, RoutineExerciseSetsVO targetSets, RoutineExerciseRepsVO targetReps) {
        this.id = id;
        this.targetSets = targetSets;
        this.targetReps = targetReps;
    }
}
