package com.standofit.back.modules.training.planning.application.command.replace_day_exercises;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.service.WorkoutDomainValidator;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseSets;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ReplaceDayExercisesService extends PlanningUseCase {

  private final WorkoutDomainValidator workoutDomainValidator;

  public ReplaceDayExercisesService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus,
      WorkoutDomainValidator workoutDomainValidator) {
    super(repository, mapper, applicationEventBus);
    this.workoutDomainValidator = workoutDomainValidator;
  }

  public void replace(ReplaceDayExercisesCommand command) {
    try {
      Workout workout = repository.getById(command.workoutId());

      List<ExerciseId> exerciseIds = new ArrayList<>();
      List<WorkoutExercise> exercises =
          command.exercises().stream()
              .map(
                  ex -> {
                    exerciseIds.add(new ExerciseId(ex.exerciseId()));
                    return WorkoutExercise.create(
                        new WorkoutExerciseId(UUID.randomUUID()),
                        new ExerciseId(ex.exerciseId()),
                        new WorkoutExerciseSets(ex.sets()),
                        new WorkoutExerciseReps(ex.reps()),
                        new WorkoutExerciseRest(ex.restSeconds()));
                  })
              .toList();

      workoutDomainValidator.ensureExercisesExist(exerciseIds);

      Workout updated = workout.updateExercisesInDay(command.dayId(), exercises);
      repository.save(updated);
      publishEvent(command.toSuccessEvent());
    } catch (Exception e) {
      publishEvent(command.toFailureEvent(resolveErrorDetail(e)));
      throw e;
    }
  }
}
