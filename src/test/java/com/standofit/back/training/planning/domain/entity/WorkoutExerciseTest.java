package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseSets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Update")
    class Update {

        @Test
        @DisplayName("should update workout exercise")
        void shouldUpdateWorkoutExercise() {
            WorkoutExercise exercise = WorkoutExerciseMother.aWorkoutExerciseWith(3, 10, 60);

            WorkoutExercise updated = exercise.update(
                    new WorkoutExerciseSets(5),
                    new WorkoutExerciseReps(8),
                    new WorkoutExerciseRest(90)
            );

            assertEquals(5, updated.getSets().value());
            assertEquals(8, updated.getReps().value());
            assertEquals(90, updated.getRestSeconds().value());
            assertEquals(exercise.getId(), updated.getId());
            assertEquals(exercise.getExerciseId(), updated.getExerciseId());
        }

        @Test
        @DisplayName("should keep other values unchanged when updating only sets")
        void shouldKeepOtherValuesUnchangedWhenUpdatingSets() {
            WorkoutExercise exercise = WorkoutExerciseMother.aWorkoutExerciseWith(3, 10, 60);

            WorkoutExercise updated = exercise.update(
                    new WorkoutExerciseSets(5),
                    exercise.getReps(),
                    exercise.getRestSeconds()
            );

            assertEquals(5, updated.getSets().value());
            assertEquals(10, updated.getReps().value());
            assertEquals(60, updated.getRestSeconds().value());
        }
    }
}
