package com.standofit.back.modules.training.execution.application.command.cancel_session;

import com.standofit.back.modules.training.execution.application.ApplicationExecutionException;
import com.standofit.back.modules.training.execution.application.ExecutionApplicationError;
import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class CancelSessionService extends ExecutionUseCase {

  public CancelSessionService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void cancel(CancelSessionCommand command) {
    try {
      Session session = repository.findById(command.sessionId());
      repository.save(session.cancel());
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_CANCELLED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_CANCELLED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_CANCELLED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_CANCELLED.getDefaultDescription(),
              e.getMessage()));
      throw new ApplicationExecutionException(
          ExecutionApplicationError.SESSION_CANCEL_FAILED,
          command.sessionId().value().toString(),
          e);
    }
  }
}
