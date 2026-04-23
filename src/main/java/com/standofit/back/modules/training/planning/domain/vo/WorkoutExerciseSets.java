package com.standofit.back.modules.training.planning.domain.vo;

import com.standofit.back.shared.domain.valueobjects.IntegerVO;

public class WorkoutExerciseSets extends IntegerVO {
    public WorkoutExerciseSets(Integer value) {
        super(value);
        isAtLeast(1);
        isBiggerThan(100);
    }
}
