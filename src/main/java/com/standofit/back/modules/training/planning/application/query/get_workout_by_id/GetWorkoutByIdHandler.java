package com.standofit.back.modules.training.planning.application.query.get_workout_by_id;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.query.WorkoutReadRepository;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureErrors;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureException;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetWorkoutByIdHandler implements QueryHandler<GetWorkoutByIdQuery, WorkoutDto> {

  private final WorkoutReadRepository readRepository;

  public GetWorkoutByIdHandler(WorkoutReadRepository readRepository) {
    this.readRepository = readRepository;
  }

  @Override
  public Class<GetWorkoutByIdQuery> queryType() {
    return GetWorkoutByIdQuery.class;
  }

  @Override
  public WorkoutDto handle(GetWorkoutByIdQuery query) {
    return readRepository
        .findById(query.workoutId())
        .orElseThrow(
            () ->
                new WorkoutInfrastructureException(
                    WorkoutInfrastructureErrors.WORKOUT_NOT_FOUND.getMessage(
                        query.workoutId().value())));
  }
}
