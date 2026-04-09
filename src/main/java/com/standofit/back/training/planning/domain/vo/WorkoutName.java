package com.standofit.back.training.planning.domain.vo;

import com.standofit.back.shared.domain.valueobjects.StringVO;

public class WorkoutName extends StringVO {
    public WorkoutName(String value) {
        super(value);
        ensureIsNotEmptyOrBlank();
    }
}
