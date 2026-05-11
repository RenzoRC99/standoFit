package com.standofit.back.modules.training.planning.application.query.get_workout_by_id;

import com.standofit.back.modules.training.planning.application.PlanningUseCase;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent;
import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
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
              .findById(query.workoutId().value())
              .map(mapper::toDto)
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "Workout not found: " + query.workoutId().value()));
      publishEvent(
          PlanningActivityEvent.success(
              "workout.queried", query.workoutId().value().toString(), "Queried workout by ID"));
      return dto;
    } catch (Exception e) {
      publishEvent(
          PlanningActivityEvent.failure(
              "workout.queried",
              query.workoutId().value().toString(),
              "Failed to query workout",
              e.getMessage()));
      throw e;
    }
  }
}
