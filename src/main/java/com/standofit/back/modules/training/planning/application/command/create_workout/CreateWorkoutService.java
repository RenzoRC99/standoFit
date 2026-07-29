package com.standofit.back.modules.training.planning.application.command.create_workout;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.service.WorkoutDomainValidator;
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

  private final WorkoutDomainValidator workoutDomainValidator;

  public CreateWorkoutService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus,
      WorkoutDomainValidator workoutDomainValidator) {
    super(repository, mapper, applicationEventBus);
    this.workoutDomainValidator = workoutDomainValidator;
  }

  public UUID create(CreateWorkoutCommand command) {
    WorkoutId workoutId = new WorkoutId(UUID.randomUUID());
    try {
      List<ExerciseId> exerciseIds = new ArrayList<>();
      List<WorkoutDay> days = new ArrayList<>();

      for (CreateWorkoutCommand.DayInput dayInput : command.days()) {
        List<WorkoutExercise> exercises = new ArrayList<>();

        for (CreateWorkoutCommand.ExerciseInput exInput : dayInput.exercises()) {
          exerciseIds.add(new ExerciseId(exInput.exerciseId()));
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

      workoutDomainValidator.ensureExercisesExist(exerciseIds);

      Workout workout =
          Workout.create(
              workoutId,
              new WorkoutDescription(command.description()),
              new WorkoutName(command.name()),
              days);
      UUID id = repository.save(workout).getId().value();
      publishEvent(command.toSuccessEvent());
      return id;
    } catch (Exception e) {
      publishEvent(command.toFailureEvent(resolveErrorDetail(e)));
      throw e;
    }
  }
}
