package com.standofit.back.modules.training.execution.application.command.add_exercise_log;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class AddExerciseLogHandler implements CommandHandler<AddExerciseLogCommand, Void> {

  private final AddExerciseLogService service;

  public AddExerciseLogHandler(AddExerciseLogService service) {
    this.service = service;
  }

  @Override
  public Class<AddExerciseLogCommand> commandType() {
    return AddExerciseLogCommand.class;
  }

  @Override
  public Void handle(AddExerciseLogCommand command) {
    service.addExerciseLog(command);
    return null;
  }
}
