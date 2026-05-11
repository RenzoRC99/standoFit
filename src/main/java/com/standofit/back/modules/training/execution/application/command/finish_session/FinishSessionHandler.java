package com.standofit.back.modules.training.execution.application.command.finish_session;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FinishSessionHandler implements CommandHandler<FinishSessionCommand, Void> {

  private final FinishSessionService service;

  public FinishSessionHandler(FinishSessionService service) {
    this.service = service;
  }

  @Override
  public Class<FinishSessionCommand> commandType() {
    return FinishSessionCommand.class;
  }

  @Override
  @Transactional
  public Void handle(FinishSessionCommand command) {
    service.finish(command);
    return null;
  }
}
