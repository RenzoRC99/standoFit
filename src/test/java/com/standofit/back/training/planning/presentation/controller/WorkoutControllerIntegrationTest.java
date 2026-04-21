package com.standofit.back.training.planning.presentation.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.standofit.back.shared.domain.bus.ApplicationBus;
import com.standofit.back.training.planning.application.command.create_workout.CreateWorkoutCommand;
import com.standofit.back.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
import com.standofit.back.training.planning.application.dto.WorkoutDto;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
@DisplayName("Workout Controller Integration Tests")
class WorkoutControllerIntegrationTest {

    @Autowired
    private ApplicationBus applicationBus;

    @Nested
    @DisplayName("Create Workout Command")
    class CreateWorkout {

        @Test
        @DisplayName("should create workout and return id")
        void shouldCreateWorkoutAndReturnId() {
            var command = new CreateWorkoutCommand(
                    "Full Body Workout",
                    "A complete full body routine",
                    List.of(
                            new CreateWorkoutCommand.DayInput("Monday",
                                    List.of(new CreateWorkoutCommand.ExerciseInput(
                                            UUID.randomUUID(), 3, 10, 60)))));

            UUID result = applicationBus.execute(command);

            assertNotNull(result);
        }

        @Test
        @DisplayName("should create and then retrieve workout")
        void shouldCreateAndRetrieveWorkout() {
            var command = new CreateWorkoutCommand(
                    "Test Workout",
                    "Description",
                    List.of(
                            new CreateWorkoutCommand.DayInput("Day 1",
                                    List.of(new CreateWorkoutCommand.ExerciseInput(
                                            UUID.randomUUID(), 3, 12, 45)))));

            UUID workoutId = applicationBus.execute(command);

            WorkoutDto workout = applicationBus.ask(new GetWorkoutByIdQuery(workoutId));

            assertNotNull(workout);
            assertEquals("Test Workout", workout.name());
            assertEquals("Description", workout.description());
            assertEquals(1, workout.days().size());
            assertEquals("Day 1", workout.days().get(0).name());
            assertEquals(1, workout.days().get(0).exercises().size());
        }

        @Test
        @DisplayName("should return 404 when workout not found")
        void shouldReturn404WhenNotFound() {
            assertThrows(IllegalArgumentException.class, () -> {
                applicationBus.ask(new GetWorkoutByIdQuery(UUID.randomUUID()));
            });
        }
    }
}