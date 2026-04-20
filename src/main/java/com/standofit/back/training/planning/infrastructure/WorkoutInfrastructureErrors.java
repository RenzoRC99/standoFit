package com.standofit.back.training.planning.infrastructure;

import com.standofit.back.shared.utils.EnumContract;

public enum WorkoutInfrastructureErrors implements EnumContract {
    SAVE_FAILED("Failed to save workout"),
    FIND_FAILED("Failed to find workout"),
    DELETE_FAILED("Failed to delete workout"),
    NOT_FOUND("Workout not found");

    private final String message;

    WorkoutInfrastructureErrors(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}