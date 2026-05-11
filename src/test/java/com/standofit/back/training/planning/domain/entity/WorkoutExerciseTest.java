package com.standofit.back.training.planning.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseSets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("WorkoutExercise Domain Tests")
class WorkoutExerciseTest {

  @Nested
  @DisplayName("Creation")
  class Creation {

    @Test
    @DisplayName("should create workout exercise with valid data")
    void shouldCreateWorkoutExerciseWithValidData() {
      WorkoutExercise exercise = WorkoutExerciseMother.aWorkoutExercise();

      assertNotNull(exercise.getId());
      assertNotNull(exercise.getExerciseId());
      assertNotNull(exercise.getSets());
      assertNotNull(exercise.getReps());
      assertNotNull(exercise.getRestSeconds());
    }

    @Test
    @DisplayName("should create workout exercise with custom values")
    void shouldCreateWorkoutExerciseWithCustomValues() {
      WorkoutExercise exercise = WorkoutExerciseMother.aWorkoutExerciseWith(5, 12, 90);

      assertEquals(5, exercise.getSets().value());
      assertEquals(12, exercise.getReps().value());
      assertEquals(90, exercise.getRestSeconds().value());
    }
  }

  @Nested
  @DisplayName("Copy")
  class Copy {

    @Test
    @DisplayName("should copy workout exercise with new values")
    void shouldCopyWorkoutExerciseWithNewValues() {
      WorkoutExercise exercise = WorkoutExerciseMother.aWorkoutExerciseWith(3, 10, 60);

      WorkoutExercise copied =
          exercise.copy(
              exercise.getId(),
              exercise.getExerciseId(),
              new WorkoutExerciseSets(5),
              new WorkoutExerciseReps(8),
              new WorkoutExerciseRest(90));

      assertEquals(5, copied.getSets().value());
      assertEquals(8, copied.getReps().value());
      assertEquals(90, copied.getRestSeconds().value());
      assertEquals(exercise.getId(), copied.getId());
      assertEquals(exercise.getExerciseId(), copied.getExerciseId());
    }
  }
}
