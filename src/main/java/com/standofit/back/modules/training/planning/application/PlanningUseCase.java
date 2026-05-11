package com.standofit.back.modules.training.planning.application;

import com.standofit.back.modules.training.planning.application.mapper.WorkoutDtoMapper;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;

public abstract class PlanningUseCase {

  protected final WorkoutRepository repository;
  protected final WorkoutDtoMapper mapper;
  protected final ApplicationEventBus applicationEventBus;

  protected PlanningUseCase(
      WorkoutRepository repository,
      WorkoutDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    this.repository = repository;
    this.mapper = mapper;
    this.applicationEventBus = applicationEventBus;
  }

  protected void publishEvent(ApplicationEvent event) {
    applicationEventBus.publish(event);
  }
}
