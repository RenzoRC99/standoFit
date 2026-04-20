package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.training.planning.domain.WorkoutDomainException;
import com.standofit.back.training.planning.domain.vo.WorkoutDayName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WorkoutDay Domain Tests")
class WorkoutDayTest {

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create workout day with valid data")
        void shouldCreateWorkoutDayWithValidData() {
            WorkoutDay day = WorkoutDayMother.aWorkoutDay();

            assertNotNull(day.getId());
            assertNotNull(day.getName());
            assertNotNull(day.getExercises());
        }
    }

    @Nested
    @DisplayName("Rename")
    class Rename {

        @Test
        @DisplayName("should rename workout day")
        void shouldRenameWorkoutDay() {
            WorkoutDay day = WorkoutDayMother.aWorkoutDay();

            WorkoutDay renamed = day.rename(new WorkoutDayName("New Name"));

            assertEquals("New Name", renamed.getName().value());
        }
    }

    @Nested
    @DisplayName("Add Exercises")
    class AddExercises {

        @Test
        @DisplayName("should add exercises to workout day")
        void shouldAddExercises() {
            WorkoutDay day = WorkoutDayMother.aWorkoutDayWithoutExercises();
            WorkoutExercise exercise = WorkoutExerciseMother.aWorkoutExercise();

            WorkoutDay updated = day.addExercises(List.of(exercise));

            assertEquals(1, updated.getExercises().size());
        }

        @Test
        @DisplayName("should fail when adding exercise that already exists")
        void shouldFailWhenAddingDuplicateExercise() {
            WorkoutDay day = WorkoutDayMother.aWorkoutDay();
            var existingExercise = day.getExercises().get(0);

            assertThatThrownBy(() -> day.addExercises(List.of(existingExercise)))
                    .isInstanceOf(WorkoutDomainException.class)
                    .hasMessageContaining("already exists");
        }
    }

    @Nested
    @DisplayName("Remove Exercises")
    class RemoveExercises {

        @Test
        @DisplayName("should remove exercises from workout day")
        void shouldRemoveExercises() {
            WorkoutDay day = WorkoutDayMother.aWorkoutDay();
            var exerciseId = day.getExercises().get(0).getId();

            WorkoutDay updated = day.removeExercises(List.of(exerciseId));

            assertTrue(updated.getExercises().isEmpty());
        }

        @Test
        @DisplayName("should fail when removing exercise id not found")
        void shouldFailWhenRemovingExerciseNotFound() {
            WorkoutDay day = WorkoutDayMother.aWorkoutDay();
            var nonExistentId = WorkoutExerciseMother.aWorkoutExercise().getId();

            assertThatThrownBy(() -> day.removeExercises(List.of(nonExistentId)))
                    .isInstanceOf(WorkoutDomainException.class)
                    .hasMessageContaining("not found");
        }
    }

    @Nested
    @DisplayName("Update Exercises")
    class UpdateExercises {

        @Test
        @DisplayName("should update exercises in workout day")
        void shouldUpdateExercises() {
            WorkoutDay day = WorkoutDayMother.aWorkoutDay();
            var originalExercise = day.getExercises().get(0);
            WorkoutExercise updatedExercise = originalExercise.copy(
                    originalExercise.getId(),
                    originalExercise.getExerciseId(),
                    new com.standofit.back.training.planning.domain.vo.WorkoutExerciseSets(5),
                    new com.standofit.back.training.planning.domain.vo.WorkoutExerciseReps(8),
                    new com.standofit.back.training.planning.domain.vo.WorkoutExerciseRest(90)
            );

            WorkoutDay updated = day.updateExercises(List.of(updatedExercise));

            assertEquals(5, updated.getExercises().get(0).getSets().value());
        }

        @Test
        @DisplayName("should fail when updating exercise id not found")
        void shouldFailWhenUpdatingExerciseNotFound() {
            WorkoutDay day = WorkoutDayMother.aWorkoutDayWithoutExercises();
            WorkoutExercise nonExistentExercise = WorkoutExerciseMother.aWorkoutExercise();

            assertThatThrownBy(() -> day.updateExercises(List.of(nonExistentExercise)))
                    .isInstanceOf(WorkoutDomainException.class)
                    .hasMessageContaining("not found");
        }
    }
}
