package com.standofit.back.modules.training.execution.application.command.update_exercise_log_reps;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateExerciseLogRepsHandler
    implements CommandHandler<UpdateExerciseLogRepsCommand, Void> {

  private final UpdateExerciseLogRepsService service;

  public UpdateExerciseLogRepsHandler(UpdateExerciseLogRepsService service) {
    this.service = service;
  }

  @Override
  public Class<UpdateExerciseLogRepsCommand> commandType() {
    return UpdateExerciseLogRepsCommand.class;
  }

  @Override
  @Transactional
  public Void handle(UpdateExerciseLogRepsCommand command) {
    service.updateReps(command);
    return null;
  }
}
