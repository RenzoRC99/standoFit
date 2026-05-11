package com.standofit.back.modules.training.execution.application.command.start_session;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class StartSessionHandler implements CommandHandler<StartSessionCommand, UUID> {

  private final SessionRepository repository;

  public StartSessionHandler(SessionRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<StartSessionCommand> commandType() {
    return StartSessionCommand.class;
  }

  @Override
  public UUID handle(StartSessionCommand command) {
    Session session = Session.create(new SessionId(UUID.randomUUID()), command.dayId());
    return repository.save(session).getId().value();
  }
}
