package com.standofit.back.modules.training.planning.application.query.get_workout_by_id;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.application.service.ExerciseEnrichmentService;
import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureErrors;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureException;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class GetWorkoutByIdService extends PlanningUseCase {

  private final ExerciseEnrichmentService enrichmentService;

  public GetWorkoutByIdService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus,
      ExerciseEnrichmentService enrichmentService) {
    super(repository, mapper, applicationEventBus);
    this.enrichmentService = enrichmentService;
  }

  public WorkoutDto findById(GetWorkoutByIdQuery query) {
    try {
      Workout workout =
          repository
              .findById(query.workoutId())
              .orElseThrow(
                  () ->
                      new WorkoutInfrastructureException(
                          WorkoutInfrastructureErrors.WORKOUT_NOT_FOUND.getMessage(
                              query.workoutId().value())));

      var exerciseMaps = enrichmentService.loadExerciseData(workout);
      WorkoutDto dto = mapper.toDto(workout, exerciseMaps.names(), exerciseMaps.muscleGroups());
      publishEvent(
          PlanningActivityEvent.success(
              PlanningActivityType.WORKOUT_QUERIED,
              query.workoutId().value().toString(),
              PlanningActivityType.WORKOUT_QUERIED.getDefaultDescription()));
      return dto;
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              PlanningActivityType.WORKOUT_QUERIED,
              query.workoutId().value().toString(),
              PlanningActivityType.WORKOUT_QUERIED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
