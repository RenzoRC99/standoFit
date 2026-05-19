package com.standofit.back.modules.training.planning.application.command.replace_day_exercises;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseSets;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ReplaceDayExercisesService extends PlanningUseCase {

  public ReplaceDayExercisesService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void replace(ReplaceDayExercisesCommand command) {
    try {
      Workout workout = repository.getById(command.workoutId());

      List<WorkoutExercise> exercises =
          command.exercises().stream()
              .map(
                  ex ->
                      WorkoutExercise.create(
                          new WorkoutExerciseId(UUID.randomUUID()),
                          new ExerciseId(ex.exerciseId()),
                          new WorkoutExerciseSets(ex.sets()),
                          new WorkoutExerciseReps(ex.reps()),
                          new WorkoutExerciseRest(ex.restSeconds())))
              .toList();

      Workout updated = workout.updateExercisesInDay(command.dayId(), exercises);
      repository.save(updated);
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_EXERCISES_REPLACED,
              command.workoutId().value().toString(),
              PlanningActivityType.WORKOUT_EXERCISES_REPLACED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_EXERCISES_REPLACED,
              command.workoutId().value().toString(),
              PlanningActivityType.WORKOUT_EXERCISES_REPLACED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
