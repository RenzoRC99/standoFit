package com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class UpdateExerciseLogSetsHandler
    implements CommandHandler<UpdateExerciseLogSetsCommand, Void> {

  private final SessionRepository repository;

  public UpdateExerciseLogSetsHandler(SessionRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<UpdateExerciseLogSetsCommand> commandType() {
    return UpdateExerciseLogSetsCommand.class;
  }

  @Override
  public Void handle(UpdateExerciseLogSetsCommand command) {
    Session session = repository.findById(command.sessionId());
    repository.save(session.updateLogSets(command.logId(), command.sets()));
    return null;
  }
}
