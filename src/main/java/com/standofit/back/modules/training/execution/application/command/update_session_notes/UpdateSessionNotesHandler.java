package com.standofit.back.modules.training.execution.application.command.update_session_notes;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class UpdateSessionNotesHandler implements CommandHandler<UpdateSessionNotesCommand, Void> {

  private final SessionRepository repository;

  public UpdateSessionNotesHandler(SessionRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<UpdateSessionNotesCommand> commandType() {
    return UpdateSessionNotesCommand.class;
  }

  @Override
  public Void handle(UpdateSessionNotesCommand command) {
    Session session = repository.findById(command.sessionId());
    repository.save(session.changeNotes(command.notes()));
    return null;
  }
}
