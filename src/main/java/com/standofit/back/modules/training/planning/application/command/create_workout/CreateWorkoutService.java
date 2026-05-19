package com.standofit.back.modules.training.planning.application.command.create_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.*;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkoutService extends PlanningUseCase {

  public CreateWorkoutService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public UUID create(CreateWorkoutCommand command) {
    WorkoutId workoutId = new WorkoutId(UUID.randomUUID());
    try {
      List<WorkoutDay> days = new ArrayList<>();

      for (CreateWorkoutCommand.DayInput dayInput : command.days()) {
        List<WorkoutExercise> exercises = new ArrayList<>();

        for (CreateWorkoutCommand.ExerciseInput exInput : dayInput.exercises()) {
          WorkoutExercise exercise =
              WorkoutExercise.create(
                  new WorkoutExerciseId(UUID.randomUUID()),
                  new ExerciseId(exInput.exerciseId()),
                  new WorkoutExerciseSets(exInput.sets()),
                  new WorkoutExerciseReps(exInput.reps()),
                  new WorkoutExerciseRest(exInput.restSeconds()));
          exercises.add(exercise);
        }

        WorkoutDay day =
            WorkoutDay.create(
                new WorkoutDayId(UUID.randomUUID()),
                new WorkoutDayName(dayInput.name()),
                exercises);
        days.add(day);
      }

      Workout workout =
          Workout.create(
              workoutId,
              new WorkoutDescription(command.description()),
              new WorkoutName(command.name()),
              days);
      UUID id = repository.save(workout).getId().value();
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_PLANNED,
              workoutId.value().toString(),
              PlanningActivityType.WORKOUT_PLANNED.getDefaultDescription()));
      return id;
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_PLANNED,
              workoutId.value().toString(),
              PlanningActivityType.WORKOUT_PLANNED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
