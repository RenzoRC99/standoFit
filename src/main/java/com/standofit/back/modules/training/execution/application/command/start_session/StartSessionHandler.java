package com.standofit.back.modules.training.execution.application.command.start_session;

import static com.standofit.back.modules.training.execution.application.ErrorDetailResolver.resolve;

import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.service.SessionDomainValidator;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class StartSessionHandler implements CommandHandler<StartSessionCommand, UUID> {

  private final SessionRepository repository;
  private final ApplicationEventBus eventBus;
  private final SessionDomainValidator sessionDomainValidator;

  public StartSessionHandler(
      SessionRepository repository,
      ApplicationEventBus eventBus,
      SessionDomainValidator sessionDomainValidator) {
    this.repository = repository;
    this.eventBus = eventBus;
    this.sessionDomainValidator = sessionDomainValidator;
  }

  @Override
  public Class<StartSessionCommand> commandType() {
    return StartSessionCommand.class;
  }

  @Override
  public UUID handle(StartSessionCommand command) {
    UUID id = UUID.randomUUID();
    try {
      sessionDomainValidator.ensureDayExists(command.dayId());
      Session session = Session.create(new SessionId(id), command.dayId());
      repository.save(session);
      eventBus.publish(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_STARTED,
              command.dayId().value().toString(),
              ExecutionActivityType.SESSION_STARTED.getDefaultDescription()));
      return id;
    } catch (Exception e) {
      eventBus.publish(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_STARTED,
              command.dayId().value().toString(),
              ExecutionActivityType.SESSION_STARTED.getDefaultDescription(),
              resolve(e)));
      throw e;
    }
  }
}
