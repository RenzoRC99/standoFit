package com.standofit.back.training.planning.domain.vo;

import com.standofit.back.shared.domain.valueobjects.IntegerVO;

public class WorkoutExerciseSets extends IntegerVO {
    public WorkoutExerciseSets(Integer value) {
        super(value);
        isBiggerThan(100);
    }
}
