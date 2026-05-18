package com.standofit.back.modules.training.planning.application.command.duplicate_workout;

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
public class DuplicateWorkoutService extends PlanningUseCase {

  public DuplicateWorkoutService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public UUID duplicate(DuplicateWorkoutCommand command) {
    try {
      Workout original = repository.getById(command.workoutId());

      List<WorkoutDay> duplicatedDays = new ArrayList<>();
      for (WorkoutDay day : original.getDays()) {
        List<WorkoutExercise> duplicatedExercises = new ArrayList<>();
        for (WorkoutExercise ex : day.getExercises()) {
          WorkoutExercise newEx =
              WorkoutExercise.create(
                  new WorkoutExerciseId(UUID.randomUUID()),
                  new ExerciseId(ex.getExerciseId().value()),
                  new WorkoutExerciseSets(ex.getSets().value()),
                  new WorkoutExerciseReps(ex.getReps().value()),
                  new WorkoutExerciseRest(ex.getRestSeconds().value()));
          duplicatedExercises.add(newEx);
        }

        WorkoutDay newDay =
            WorkoutDay.create(
                new WorkoutDayId(UUID.randomUUID()),
                new WorkoutDayName(day.getName().value()),
                duplicatedExercises);
        duplicatedDays.add(newDay);
      }

      WorkoutName newName =
          command.newName() != null && !command.newName().isBlank()
              ? new WorkoutName(command.newName())
              : new WorkoutName(original.getName().value() + " (Copy)");

      Workout duplicated =
          Workout.create(
              new WorkoutId(UUID.randomUUID()),
              original.getDescription() != null
                  ? new WorkoutDescription(original.getDescription().value())
                  : new WorkoutDescription(""),
              newName,
              duplicatedDays);

      UUID id = repository.save(duplicated).getId().value();
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_DUPLICATED,
              id.toString(),
              PlanningActivityType.WORKOUT_DUPLICATED.getDefaultDescription()));
      return id;
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_DUPLICATED,
              command.workoutId().toString(),
              PlanningActivityType.WORKOUT_DUPLICATED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
