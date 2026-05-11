package com.standofit.back.modules.training.planning.application.command.archive_workout;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ArchiveWorkoutHandler implements CommandHandler<ArchiveWorkoutCommand, Void> {

  private final ArchiveWorkoutService service;

  public ArchiveWorkoutHandler(ArchiveWorkoutService service) {
    this.service = service;
  }

  @Override
  public Class<ArchiveWorkoutCommand> commandType() {
    return ArchiveWorkoutCommand.class;
  }

  @Override
  @Transactional
  public Void handle(ArchiveWorkoutCommand command) {
    service.archive(command);
    return null;
  }
}
