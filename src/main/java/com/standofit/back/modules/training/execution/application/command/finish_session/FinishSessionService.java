package com.standofit.back.modules.training.execution.application.command.finish_session;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class FinishSessionService extends ExecutionUseCase {

  public FinishSessionService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void finish(FinishSessionCommand command) {
    try {
      Session session = repository.getById(command.sessionId());
      repository.save(session.finish());
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_FINISHED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_FINISHED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_FINISHED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_FINISHED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
