package com.standofit.back.modules.training.execution.application.command.delete_session;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class DeleteSessionService extends ExecutionUseCase {

  public DeleteSessionService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void delete(DeleteSessionCommand command) {
    try {
      repository.delete(command.sessionId());
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_DELETED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_DELETED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_DELETED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_DELETED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
