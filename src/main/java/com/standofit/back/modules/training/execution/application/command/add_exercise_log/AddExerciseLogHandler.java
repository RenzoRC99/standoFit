package com.standofit.back.modules.training.execution.application.command.add_exercise_log;

import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class AddExerciseLogHandler implements CommandHandler<AddExerciseLogCommand, Void> {

  private final SessionRepository repository;

  public AddExerciseLogHandler(SessionRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<AddExerciseLogCommand> commandType() {
    return AddExerciseLogCommand.class;
  }

  @Override
  public Void handle(AddExerciseLogCommand command) {
    Session session = repository.findById(command.sessionId());
    ExerciseLog log =
        ExerciseLog.create(
            command.logId(),
            command.exerciseId(),
            command.sets(),
            command.reps(),
            command.weight());
    repository.save(session.addLog(log));
    return null;
  }
}
