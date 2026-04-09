package com.standofit.back.training.planning.domain.vo;

import com.standofit.back.shared.domain.valueobjects.IntegerVO;

public class WorkoutExerciseRest extends IntegerVO {
    public WorkoutExerciseRest(Integer value) {
        super(value);
        isBiggerThan(3600);
    }
}
