package com.standofit.back.training.planning.domain;

public enum WorkoutDayErrors {

    EXERCISE_ID_NOT_FOUND("Exercise with id '%s' not found"),
    EXERCISE_ID_ALREADY_EXISTS("Exercise with id '%s' already exists");

    private final String message;

    WorkoutDayErrors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}
