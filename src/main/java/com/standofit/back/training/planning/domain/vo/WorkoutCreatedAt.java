package com.standofit.back.training.planning.domain.vo;

import com.standofit.back.shared.domain.valueobjects.DateTimeVO;

import java.time.Instant;

public class WorkoutCreatedAt extends DateTimeVO {
    public WorkoutCreatedAt(Instant value) {
        super(value);
    }
}
