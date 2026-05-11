package com.standofit.back.modules.training.execution.application.command.start_session;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StartSessionHandler implements CommandHandler<StartSessionCommand, UUID> {

  private final StartSessionService service;

  public StartSessionHandler(StartSessionService service) {
    this.service = service;
  }

  @Override
  public Class<StartSessionCommand> commandType() {
    return StartSessionCommand.class;
  }

  @Override
  @Transactional
  public UUID handle(StartSessionCommand command) {
    return service.startSession(command);
  }
}
