package com.standofit.back.modules.training.planning.application.query.get_workout_by_id;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetWorkoutByIdHandler implements QueryHandler<GetWorkoutByIdQuery, WorkoutDto> {

  private final GetWorkoutByIdService service;

  public GetWorkoutByIdHandler(GetWorkoutByIdService service) {
    this.service = service;
  }

  @Override
  public Class<GetWorkoutByIdQuery> queryType() {
    return GetWorkoutByIdQuery.class;
  }

  @Override
  public WorkoutDto handle(GetWorkoutByIdQuery query) {
    return service.findById(query);
  }
}
