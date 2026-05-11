package com.standofit.back.modules.training.execution.application.command.delete_session;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteSessionHandler implements CommandHandler<DeleteSessionCommand, Void> {

  private final DeleteSessionService service;

  public DeleteSessionHandler(DeleteSessionService service) {
    this.service = service;
  }

  @Override
  public Class<DeleteSessionCommand> commandType() {
    return DeleteSessionCommand.class;
  }

  @Override
  @Transactional
  public Void handle(DeleteSessionCommand command) {
    service.delete(command);
    return null;
  }
}
