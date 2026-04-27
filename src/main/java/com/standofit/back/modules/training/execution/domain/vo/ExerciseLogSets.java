package com.standofit.back.modules.training.execution.domain.vo;

import com.standofit.back.shared.domain.valueobjects.IntegerVO;

public class ExerciseLogSets extends IntegerVO {
    public ExerciseLogSets(int value) {
        super(value);
        isBiggerThan(100);
    }
}