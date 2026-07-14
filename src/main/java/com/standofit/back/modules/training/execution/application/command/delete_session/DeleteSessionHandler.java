package com.standofit.back.modules.training.execution.application.command.delete_session;

import static com.standofit.back.modules.training.execution.application.ErrorDetailResolver.resolve;

import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.query.SessionReadViewUpdater;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class DeleteSessionHandler implements VoidCommandHandler<DeleteSessionCommand> {

  private final SessionRepository repository;
  private final SessionReadViewUpdater readViewUpdater;
  private final ApplicationEventBus eventBus;

  public DeleteSessionHandler(
      SessionRepository repository,
      SessionReadViewUpdater readViewUpdater,
      ApplicationEventBus eventBus) {
    this.repository = repository;
    this.readViewUpdater = readViewUpdater;
    this.eventBus = eventBus;
  }

  @Override
  public Class<DeleteSessionCommand> commandType() {
    return DeleteSessionCommand.class;
  }

  @Override
  public void execute(DeleteSessionCommand command) {
    try {
      repository.deleteById(command.sessionId());
      readViewUpdater.remove(command.sessionId().value());
      eventBus.publish(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_DELETED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_DELETED.getDefaultDescription()));
    } catch (Exception e) {
      eventBus.publish(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_DELETED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_DELETED.getDefaultDescription(),
              resolve(e)));
      throw e;
    }
  }
}
