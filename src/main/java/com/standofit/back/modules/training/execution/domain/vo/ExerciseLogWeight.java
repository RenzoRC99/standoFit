package com.standofit.back.modules.training.execution.domain.vo;

import com.standofit.back.shared.domain.valueobjects.IntegerVO;

public class ExerciseLogWeight extends IntegerVO {
    public ExerciseLogWeight(int value) {
        super(value);
        isBiggerThan(1000);
    }
}
