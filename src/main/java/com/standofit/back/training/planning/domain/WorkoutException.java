package com.standofit.back.training.planning.domain;

import com.standofit.back.shared.domain.DomainException;

public class WorkoutException extends DomainException {

    public WorkoutException(WorkoutErrors error, String... args) {
        super(WorkoutException.class, error.format((Object[]) args));
    }
}
