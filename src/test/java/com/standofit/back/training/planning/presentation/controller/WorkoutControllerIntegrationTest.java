package com.standofit.back.training.planning.presentation.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.standofit.back.configuration.bus.ApplicationBusFacade;
import com.standofit.back.modules.training.planning.application.command.plan_workout.PlanWorkoutCommand;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
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

  @Autowired private ApplicationBusFacade applicationBus;

  @Nested
  @DisplayName("Plan Workout Command")
  class PlanWorkout {

    @Test
    @DisplayName("should create workout and return id")
    void shouldCreateWorkoutAndReturnId() {
      var command =
          new PlanWorkoutCommand(
              "Full Body Workout",
              "A complete full body routine",
              List.of(
                  new PlanWorkoutCommand.DayInput(
                      "Monday",
                      List.of(
                          new PlanWorkoutCommand.ExerciseInput(UUID.randomUUID(), 3, 10, 60)))));

      UUID result = applicationBus.execute(command);

      assertNotNull(result);
    }

    @Test
    @DisplayName("should create and then retrieve workout")
    void shouldCreateAndRetrieveWorkout() {
      var command =
          new PlanWorkoutCommand(
              "Test Workout",
              "Description",
              List.of(
                  new PlanWorkoutCommand.DayInput(
                      "Day 1",
                      List.of(
                          new PlanWorkoutCommand.ExerciseInput(UUID.randomUUID(), 3, 12, 45)))));

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
    @DisplayName("should return error when workout not found")
    void shouldReturnErrorWhenNotFound() {
      assertThrows(
          IllegalArgumentException.class,
          () -> {
            applicationBus.ask(new GetWorkoutByIdQuery(UUID.randomUUID()));
          });
    }
  }
}
