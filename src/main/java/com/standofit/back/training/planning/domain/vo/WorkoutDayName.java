package com.standofit.back.training.planning.domain.vo;

import com.standofit.back.shared.domain.valueobjects.StringVO;

public class WorkoutDayName extends StringVO {
    public WorkoutDayName(String value) {
        super(value);
        ensureIsNotEmptyOrBlank();
    }
}
