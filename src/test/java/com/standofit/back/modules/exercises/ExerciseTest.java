package com.standofit.back.modules.exercises;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Exercise Tests")
class ExerciseTest {

  @Test
  @DisplayName("should create exercise")
  void shouldCreateExercise() {
    Exercise exercise = new Exercise();
    exercise.setName("Bench Press");
    exercise.setDescription("Chest exercise");
    exercise.setMuscleGroup(ExerciseMuscleGroup.CHEST);

    assertEquals("Bench Press", exercise.getName());
    assertEquals("Chest exercise", exercise.getDescription());
    assertEquals(ExerciseMuscleGroup.CHEST, exercise.getMuscleGroup());
  }
}
