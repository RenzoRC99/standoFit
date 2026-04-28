package com.standofit.back.modules.training.execution.application.command.delete_session;

import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class DeleteSessionHandler implements CommandHandler<DeleteSessionCommand, Void> {

  private final SessionRepository repository;

  public DeleteSessionHandler(SessionRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<DeleteSessionCommand> commandType() {
    return DeleteSessionCommand.class;
  }

  @Override
  public Void handle(DeleteSessionCommand command) {
    repository.delete(command.sessionId());
    return null;
  }
}
