package com.standofit.back.modules.training.execution.application.command.update_exercise_log_weight;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class UpdateExerciseLogWeightHandler
    implements CommandHandler<UpdateExerciseLogWeightCommand, Void> {

  private final SessionRepository repository;

  public UpdateExerciseLogWeightHandler(SessionRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<UpdateExerciseLogWeightCommand> commandType() {
    return UpdateExerciseLogWeightCommand.class;
  }

  @Override
  public Void handle(UpdateExerciseLogWeightCommand command) {
    Session session = repository.findById(command.sessionId());
    repository.save(session.updateLogWeight(command.logId(), command.weight()));
    return null;
  }
}
