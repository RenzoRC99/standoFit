package com.standofit.back.shared.domain.valueobjects.ids;

import com.standofit.back.shared.domain.valueobjects.Id;

import java.util.UUID;

public final class WorkoutExerciseId extends Id {
    public WorkoutExerciseId(UUID value) {
        super(value);
    }
}