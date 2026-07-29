package com.standofit.back.modules.training.execution.application.command.cancel_session;

import static com.standofit.back.modules.training.execution.application.ErrorDetailResolver.resolve;

import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.query.SessionReadViewUpdater;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CancelSessionHandler implements VoidCommandHandler<CancelSessionCommand> {

  private final SessionRepository repository;
  private final SessionReadViewUpdater readViewUpdater;
  private final ApplicationEventBus eventBus;

  public CancelSessionHandler(
      SessionRepository repository,
      SessionReadViewUpdater readViewUpdater,
      ApplicationEventBus eventBus) {
    this.repository = repository;
    this.readViewUpdater = readViewUpdater;
    this.eventBus = eventBus;
  }

  @Override
  public Class<CancelSessionCommand> commandType() {
    return CancelSessionCommand.class;
  }

  @Override
  public void execute(CancelSessionCommand command) {
    try {
      Session session = repository.getById(command.sessionId());
      Session saved = repository.save(session.cancel());
      readViewUpdater.upsert(saved);
      eventBus.publish(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_CANCELLED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_CANCELLED.getDefaultDescription()));
    } catch (Exception e) {
      eventBus.publish(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_CANCELLED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_CANCELLED.getDefaultDescription(),
              resolve(e)));
      throw e;
    }
  }
}
