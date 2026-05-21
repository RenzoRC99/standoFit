package com.standofit.back.modules.training.execution.application.command.remove_exercise_log;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class RemoveExerciseLogHandler implements CommandHandler<RemoveExerciseLogCommand, Void> {

  private final RemoveExerciseLogService service;

  public RemoveExerciseLogHandler(RemoveExerciseLogService service) {
    this.service = service;
  }

  @Override
  public Class<RemoveExerciseLogCommand> commandType() {
    return RemoveExerciseLogCommand.class;
  }

  @Override
  public Void handle(RemoveExerciseLogCommand command) {
    service.removeExerciseLog(command);
    return null;
  }
}
