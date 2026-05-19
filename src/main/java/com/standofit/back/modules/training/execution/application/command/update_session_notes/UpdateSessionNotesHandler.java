package com.standofit.back.modules.training.execution.application.command.update_session_notes;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateSessionNotesHandler implements CommandHandler<UpdateSessionNotesCommand, Void> {

  private final UpdateSessionNotesService service;

  public UpdateSessionNotesHandler(UpdateSessionNotesService service) {
    this.service = service;
  }

  @Override
  public Class<UpdateSessionNotesCommand> commandType() {
    return UpdateSessionNotesCommand.class;
  }

  @Override
  public Void handle(UpdateSessionNotesCommand command) {
    service.updateNotes(command);
    return null;
  }
}
