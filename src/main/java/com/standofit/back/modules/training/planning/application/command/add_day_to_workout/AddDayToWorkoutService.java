package com.standofit.back.modules.training.planning.application.command.add_day_to_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutDayName;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseSets;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AddDayToWorkoutService extends PlanningUseCase {

  public AddDayToWorkoutService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void addDay(AddDayToWorkoutCommand command) {
    try {
      Workout workout =
          repository
              .findById(command.workoutId())
              .orElseThrow(
                  () -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

      List<WorkoutExercise> exercises = new ArrayList<>();
      for (AddDayToWorkoutCommand.ExerciseInput exInput : command.exercises()) {
        exercises.add(
            WorkoutExercise.create(
                new WorkoutExerciseId(UUID.randomUUID()),
                new ExerciseId(exInput.exerciseId()),
                new WorkoutExerciseSets(exInput.sets()),
                new WorkoutExerciseReps(exInput.reps()),
                new WorkoutExerciseRest(exInput.restSeconds())));
      }

      WorkoutDay newDay =
          WorkoutDay.create(
              new WorkoutDayId(UUID.randomUUID()),
              new WorkoutDayName(command.dayName()),
              exercises);
      Workout updatedWorkout = workout.addDays(List.of(newDay));
      repository.save(updatedWorkout);
      publishEvent(
          PlanningActivityEvent.success(
              "workout.day.added",
              command.workoutId().toString(),
              "Added day: " + command.dayName()));
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              "workout.day.added",
              command.workoutId().toString(),
              "Failed to add day",
              e.getMessage()));
      throw e;
    }
  }
}
