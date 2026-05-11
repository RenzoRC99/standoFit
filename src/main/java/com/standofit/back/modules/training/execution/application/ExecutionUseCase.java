package com.standofit.back.modules.training.execution.application;

import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;

public abstract class ExecutionUseCase {

  protected final SessionRepository repository;
  protected final SessionDtoMapper mapper;
  protected final ApplicationEventBus applicationEventBus;

  protected ExecutionUseCase(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    this.repository = repository;
    this.mapper = mapper;
    this.applicationEventBus = applicationEventBus;
  }

  protected void publishEvent(ApplicationEvent event) {
    applicationEventBus.publish(event);
  }
}
