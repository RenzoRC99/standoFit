package com.standofit.back.training.planning.domain;

import com.standofit.back.shared.domain.DomainException;

public class WorkoutDayException extends DomainException {

    public WorkoutDayException(WorkoutDayErrors error, String... args) {
        super(WorkoutDayException.class, error.format((Object[]) args));
    }
}
