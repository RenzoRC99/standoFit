package com.standofit.back.modules.training.planning.presentation.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.standofit.back.modules.training.planning.application.command.create_workout.CreateWorkoutCommand;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureException;
import com.standofit.back.shared.domain.bus.command.CommandBus;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
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

  @Autowired private CommandBus commandBus;
  @Autowired private QueryBus queryBus;

  @Nested
  @DisplayName("Create Workout Command")
  class CreateWorkout {

    @Test
    @DisplayName("should create workout and return id")
    void shouldCreateWorkoutAndReturnId() {
      var command =
          new CreateWorkoutCommand(
              "Full Body Workout",
              "A complete full body routine",
              List.of(
                  new CreateWorkoutCommand.DayInput(
                      "Monday",
                      List.of(
                          new CreateWorkoutCommand.ExerciseInput(UUID.randomUUID(), 3, 10, 60)))));

      UUID result = commandBus.dispatch(command);

      assertNotNull(result);
    }

    @Test
    @DisplayName("should create and then retrieve workout")
    void shouldCreateAndRetrieveWorkout() {
      var command =
          new CreateWorkoutCommand(
              "Test Workout",
              "Description",
              List.of(
                  new CreateWorkoutCommand.DayInput(
                      "Day 1",
                      List.of(
                          new CreateWorkoutCommand.ExerciseInput(UUID.randomUUID(), 3, 12, 45)))));

      UUID workoutId = commandBus.dispatch(command);

      WorkoutDto workout = queryBus.ask(new GetWorkoutByIdQuery(new WorkoutId(workoutId)));

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
          WorkoutInfrastructureException.class,
          () -> {
            queryBus.ask(new GetWorkoutByIdQuery(new WorkoutId(UUID.randomUUID())));
          });
    }
  }
}
