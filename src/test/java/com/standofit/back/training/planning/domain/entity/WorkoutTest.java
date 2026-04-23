package com.standofit.back.training.planning.domain.entity;

import com.standofit.back.modules.training.planning.domain.WorkoutDomainException;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.vo.*;
import com.standofit.back.shared.domain.valueobjects.errors.ValueObjectException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Workout Domain Tests")
class WorkoutTest {

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create workout with valid data")
        void shouldCreateWorkoutWithValidData() {
            Workout workout = WorkoutMother.aWorkout();

            assertNotNull(workout.getId());
            assertNotNull(workout.getName());
            assertNotNull(workout.getDescription());
            assertNotNull(workout.getDays());
            assertNotNull(workout.getCreatedAt());
            assertNotNull(workout.getUpdatedAt());
        }

        @Test
        @DisplayName("should create workout with custom name using factory method")
        void shouldCreateWorkoutWithCustomName() {
            Workout workout = WorkoutMother.aWorkoutWithName("Leg Day");

            assertEquals("Leg Day", workout.getName().value());
        }

        @Test
        @DisplayName("should fail when creating workout with no days")
        void shouldFailWhenCreatingWithNoDays() {
            assertThatThrownBy(WorkoutMother::aWorkoutWithNoDays)
                    .isInstanceOf(WorkoutDomainException.class)
                    .hasMessageContaining("Days cannot be null or empty");
        }
    }

    @Nested
    @DisplayName("Change Description")
    class ChangeDescription {

        @Test
        @DisplayName("should keep other properties unchanged after changing description")
        void shouldKeepOtherPropertiesAfterChangingDescription() {
            Workout original = WorkoutMother.aWorkout();
            WorkoutDescription newDescription = new WorkoutDescription("Updated description");

            Workout updated = original.changeDescription(newDescription);

            assertEquals(original.getId(), updated.getId());
            assertEquals(original.getName(), updated.getName());
            assertEquals(original.getDays().size(), updated.getDays().size());
        }

        @Test
        @DisplayName("should change description to new value")
        void shouldChangeDescriptionToNewValue() {
            Workout workout = WorkoutMother.aWorkout();

            Workout updated = workout.changeDescription(new WorkoutDescription("New Desc"));

            assertEquals("New Desc", updated.getDescription().value());
        }
    }

    @Nested
    @DisplayName("Rename Workout")
    class RenameWorkout {

        @Test
        @DisplayName("should rename workout name")
        void shouldRenameWorkout() {
            Workout original = WorkoutMother.aWorkout();
            WorkoutName newName = new WorkoutName("Chest Day");

            Workout renamed = original.renameWorkout(newName);

            assertEquals(newName, renamed.getName());
        }

        @Test
        @DisplayName("should keep other properties unchanged after rename")
        void shouldKeepOtherPropertiesAfterRename() {
            Workout original = WorkoutMother.aWorkout();
            WorkoutName newName = new WorkoutName("New Name");

            Workout renamed = original.renameWorkout(newName);

            assertEquals(original.getId(), renamed.getId());
            assertEquals(original.getDescription(), renamed.getDescription());
            assertEquals(original.getDays().size(), renamed.getDays().size());
        }
    }

    @Nested
    @DisplayName("Manage Days")
    class ManageDays {

        @Test
        @DisplayName("should add new days to workout")
        void shouldAddNewDays() {
            Workout original = WorkoutMother.aWorkout();
            WorkoutDay newDay = WorkoutDayMother.aWorkoutDayWithoutExercises("New Day");
            int originalSize = original.getDays().size();

            Workout updated = original.addDays(List.of(newDay));

            assertEquals(originalSize + 1, updated.getDays().size());
        }

        @Test
        @DisplayName("should fail when adding empty days list")
        void shouldFailWhenAddingEmptyDays() {
            Workout original = WorkoutMother.aWorkout();

            assertThatThrownBy(() -> original.addDays(List.of()))
                    .isInstanceOf(WorkoutDomainException.class);
        }

        @Test
        @DisplayName("should fail when adding null days list")
        void shouldFailWhenAddingNullDaysList() {
            Workout original = WorkoutMother.aWorkout();

            assertThatThrownBy(() -> original.addDays(null))
                    .isInstanceOf(WorkoutDomainException.class);
        }

        @Test
        @DisplayName("should remove days from workout")
        void shouldRemoveDays() {
            Workout workout =
                    WorkoutMother.aWorkoutWithDays(
                            List.of(
                                    WorkoutDayMother.aWorkoutDay(),
                                    WorkoutDayMother.aWorkoutDayWithoutExercises("Day 2")));

            Workout updated = workout.removeDays(List.of(workout.getDays().get(0).getId()));

            assertEquals(1, updated.getDays().size());
        }

        @Test
        @DisplayName("should fail when removing day id not found")
        void shouldFailWhenRemovingDayIdNotFound() {
            WorkoutDay day1 = WorkoutDayMother.aWorkoutDay(new WorkoutDayName("Day 1"));
            Workout workout = WorkoutMother.aWorkoutWithDays(List.of(day1));

            var nonExistentId = WorkoutDayMother.aWorkoutDayWithoutExercises().getId();

            assertThatThrownBy(() -> workout.removeDays(List.of(nonExistentId)))
                    .isInstanceOf(WorkoutDomainException.class)
                    .hasMessageContaining("not found");
        }

        @Test
        @DisplayName("should fail when removing with null day ids")
        void shouldFailWhenRemovingWithNullDayIds() {
            Workout workout = WorkoutMother.aWorkout();

            assertThatThrownBy(() -> workout.removeDays(null))
                    .isInstanceOf(WorkoutDomainException.class);
        }

        @Test
        @DisplayName("should rename days")
        void shouldRenameDays() {
            Workout workout = WorkoutMother.aWorkoutWithDays(List.of(WorkoutDayMother.aWorkoutDay()));
            WorkoutDayName newName = new WorkoutDayName("New Day Name");

            Workout updated =
                    workout.renameDays(java.util.Map.of(workout.getDays().get(0).getId(), newName));

            assertEquals(newName, updated.getDays().get(0).getName());
        }

        @Test
        @DisplayName("should fail when renaming with null map")
        void shouldFailWhenRenamingWithNullMap() {
            Workout workout = WorkoutMother.aWorkout();

            assertThatThrownBy(() -> workout.renameDays(null))
                    .isInstanceOf(WorkoutDomainException.class);
        }
    }

    @Nested
    @DisplayName("Manage Exercises")
    class ManageExercises {

        @Test
        @DisplayName("should add exercises to a day")
        void shouldAddExercisesToDay() {
            Workout workout =
                    WorkoutMother.aWorkoutWithDays(
                            List.of(WorkoutDayMother.aWorkoutDayWithExercises(List.of())));
            var dayId = workout.getDays().get(0).getId();
            WorkoutExercise newExercise = WorkoutExerciseMother.aWorkoutExercise();

            Workout updated = workout.addExercisesToDay(dayId, List.of(newExercise));

            assertEquals(1, updated.getDays().get(0).getExercises().size());
        }

        @Test
        @DisplayName("should fail when adding exercises with null day id")
        void shouldFailWhenAddingExercisesWithNullDayId() {
            Workout workout = WorkoutMother.aWorkout();

            assertThatThrownBy(() -> workout.addExercisesToDay(null, List.of()))
                    .isInstanceOf(WorkoutDomainException.class);
        }

        @Test
        @DisplayName("should update exercises in a day")
        void shouldUpdateExercisesInDay() {
            WorkoutExercise exercise = WorkoutExerciseMother.aWorkoutExerciseWith(3, 10, 60);
            Workout workout =
                    WorkoutMother.aWorkoutWithDays(
                            List.of(WorkoutDayMother.aWorkoutDayWithExercises(List.of(exercise))));
            var dayId = workout.getDays().get(0).getId();
            WorkoutExercise updatedExercise =
                    exercise.copy(
                            exercise.getId(),
                            exercise.getExerciseId(),
                            new WorkoutExerciseSets(5),
                            new WorkoutExerciseReps(8),
                            new WorkoutExerciseRest(90));

            Workout updated = workout.updateExercisesInDay(dayId, List.of(updatedExercise));

            assertEquals(5, updated.getDays().get(0).getExercises().get(0).getSets().value());
        }

        @Test
        @DisplayName("should remove exercises from a day")
        void shouldRemoveExercisesFromDay() {
            WorkoutExercise exercise = WorkoutExerciseMother.aWorkoutExercise();
            Workout workout =
                    WorkoutMother.aWorkoutWithDays(
                            List.of(WorkoutDayMother.aWorkoutDayWithExercises(List.of(exercise))));
            var dayId = workout.getDays().get(0).getId();

            Workout updated = workout.removeExercisesFromDay(dayId, List.of(exercise.getId()));

            assertTrue(updated.getDays().get(0).getExercises().isEmpty());
        }
    }

    @Nested
    @DisplayName("Reorder Days")
    class ReorderDays {

        @Test
        @DisplayName("should reorder days")
        void shouldReorderDays() {
            WorkoutDay day1 = WorkoutDayMother.aWorkoutDay(new WorkoutDayName("Day 1"));
            WorkoutDay day2 = WorkoutDayMother.aWorkoutDay(new WorkoutDayName("Day 2"));
            Workout workout = WorkoutMother.aWorkoutWithDays(List.of(day1, day2));

            Workout reordered = workout.reorderDays(List.of(day2.getId(), day1.getId()));

            assertEquals("Day 2", reordered.getDays().get(0).getName().value());
            assertEquals("Day 1", reordered.getDays().get(1).getName().value());
        }

        @Test
        @DisplayName("should fail when reorder with null list")
        void shouldFailWhenReorderWithNullList() {
            Workout workout = WorkoutMother.aWorkout();

            assertThatThrownBy(() -> workout.reorderDays(null))
                    .isInstanceOf(WorkoutDomainException.class);
        }

        @Test
        @DisplayName("should fail when reorder has wrong number of ids")
        void shouldFailWhenReorderHasWrongNumberOfIds() {
            Workout workout =
                    WorkoutMother.aWorkoutWithDays(
                            List.of(
                                    WorkoutDayMother.aWorkoutDay(),
                                    WorkoutDayMother.aWorkoutDayWithoutExercises("Day 2")));

            assertThatThrownBy(() -> workout.reorderDays(List.of(workout.getDays().get(0).getId())))
                    .isInstanceOf(WorkoutDomainException.class)
                    .hasMessageContaining("Days count mismatch");
        }

        @Test
        @DisplayName("should fail when reorder id not found")
        void shouldFailWhenReorderIdNotFound() {
            WorkoutDay day1 = WorkoutDayMother.aWorkoutDay(new WorkoutDayName("Day 1"));
            Workout workout = WorkoutMother.aWorkoutWithDays(List.of(day1));

            var nonExistentId = WorkoutDayMother.aWorkoutDayWithoutExercises().getId();

            assertThatThrownBy(() -> workout.reorderDays(List.of(nonExistentId)))
                    .isInstanceOf(WorkoutDomainException.class)
                    .hasMessageContaining("not found");
        }
    }

    @Nested
    @DisplayName("Business Rules")
    class BusinessRules {

        @Test
        @DisplayName("should fail when renaming day id not found")
        void shouldFailWhenRenamingDayIdNotFound() {
            WorkoutDay day1 = WorkoutDayMother.aWorkoutDay(new WorkoutDayName("Day 1"));
            Workout workout = WorkoutMother.aWorkoutWithDays(List.of(day1));

            var nonExistentId = WorkoutDayMother.aWorkoutDayWithoutExercises().getId();

            assertThatThrownBy(
                    () -> workout.renameDays(Map.of(nonExistentId, new WorkoutDayName("New Name"))))
                    .isInstanceOf(WorkoutDomainException.class)
                    .hasMessageContaining("not found");
        }
    }

    @Nested
    @DisplayName("Value Object Validations")
    class ValueObjectValidations {

        @Test
        @DisplayName("should fail when workout name is blank")
        void shouldFailWhenWorkoutNameIsBlank() {
            assertThatThrownBy(() -> new WorkoutName("   ")).isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when workout name is empty")
        void shouldFailWhenWorkoutNameIsEmpty() {
            assertThatThrownBy(() -> new WorkoutName("")).isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when workout name exceeds max length")
        void shouldFailWhenWorkoutNameTooLong() {
            String longName = "a".repeat(101);
            assertThatThrownBy(() -> new WorkoutName(longName)).isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when workout description exceeds max length")
        void shouldFailWhenWorkoutDescriptionTooLong() {
            String longDesc = "a".repeat(501);
            assertThatThrownBy(() -> new WorkoutDescription(longDesc))
                    .isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when day name is blank")
        void shouldFailWhenDayNameIsBlank() {
            assertThatThrownBy(() -> new WorkoutDayName("   ")).isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when day name exceeds max length")
        void shouldFailWhenDayNameTooLong() {
            String longName = "a".repeat(51);
            assertThatThrownBy(() -> new WorkoutDayName(longName))
                    .isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when sets is zero")
        void shouldFailWhenSetsIsZero() {
            assertThatThrownBy(() -> new WorkoutExerciseSets(0)).isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when sets is negative")
        void shouldFailWhenSetsIsNegative() {
            assertThatThrownBy(() -> new WorkoutExerciseSets(-1))
                    .isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when sets exceeds max")
        void shouldFailWhenSetsTooBig() {
            assertThatThrownBy(() -> new WorkoutExerciseSets(101))
                    .isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when reps is zero")
        void shouldFailWhenRepsIsZero() {
            assertThatThrownBy(() -> new WorkoutExerciseReps(0)).isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when reps exceeds max")
        void shouldFailWhenRepsTooBig() {
            assertThatThrownBy(() -> new WorkoutExerciseReps(1001))
                    .isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when rest is negative")
        void shouldFailWhenRestIsNegative() {
            assertThatThrownBy(() -> new WorkoutExerciseRest(-1))
                    .isInstanceOf(ValueObjectException.class);
        }

        @Test
        @DisplayName("should fail when rest exceeds max")
        void shouldFailWhenRestTooBig() {
            assertThatThrownBy(() -> new WorkoutExerciseRest(3601))
                    .isInstanceOf(ValueObjectException.class);
        }
    }
}
