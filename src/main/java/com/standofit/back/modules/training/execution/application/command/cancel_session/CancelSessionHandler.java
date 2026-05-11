package com.standofit.back.modules.training.execution.application.command.cancel_session;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CancelSessionHandler implements CommandHandler<CancelSessionCommand, Void> {

  private final CancelSessionService service;

  public CancelSessionHandler(CancelSessionService service) {
    this.service = service;
  }

  @Override
  public Class<CancelSessionCommand> commandType() {
    return CancelSessionCommand.class;
  }

  @Override
  @Transactional
  public Void handle(CancelSessionCommand command) {
    service.cancel(command);
    return null;
  }
}
