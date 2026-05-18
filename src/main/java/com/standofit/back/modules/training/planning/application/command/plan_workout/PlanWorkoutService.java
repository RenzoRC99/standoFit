package com.standofit.back.modules.training.planning.application.command.plan_workout;

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
public class PlanWorkoutService extends PlanningUseCase {

  public PlanWorkoutService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public UUID plan(PlanWorkoutCommand command) {
    try {
      List<WorkoutDay> days = new ArrayList<>();

      for (PlanWorkoutCommand.DayInput dayInput : command.days()) {
        List<WorkoutExercise> exercises = new ArrayList<>();

        for (PlanWorkoutCommand.ExerciseInput exInput : dayInput.exercises()) {
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
              new WorkoutId(UUID.randomUUID()),
              new WorkoutDescription(command.description()),
              new WorkoutName(command.name()),
              days);

      UUID id = repository.save(workout).getId().value();
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_PLANNED,
              id.toString(),
              PlanningActivityType.WORKOUT_PLANNED.getDefaultDescription()));
      return id;
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_PLANNED,
              null,
              PlanningActivityType.WORKOUT_PLANNED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
