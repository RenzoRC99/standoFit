package com.standofit.back.modules.training.planning.application.query.get_workout_by_id;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityType;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureErrors;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureException;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class GetWorkoutByIdService extends PlanningUseCase {

  public GetWorkoutByIdService(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public WorkoutDto findById(GetWorkoutByIdQuery query) {
    try {
      WorkoutDto dto =
          repository
              .findById(query.workoutId())
              .map(mapper::toDto)
              .orElseThrow(
                  () ->
                      new WorkoutInfrastructureException(
                          WorkoutInfrastructureErrors.WORKOUT_NOT_FOUND.getMessage(
                              query.workoutId().value())));
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
