package com.standofit.back.modules.training.planning.infrastructure;

public class WorkoutNotFoundException extends RuntimeException {
  public WorkoutNotFoundException(String id) {
    super("Workout not found: " + id);
  }
}
