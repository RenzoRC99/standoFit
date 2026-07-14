package com.standofit.back.modules.training.execution.application.command.add_exercise_log;

import static com.standofit.back.modules.training.execution.application.ErrorDetailResolver.resolve;

import com.standofit.back.modules.exercises.repository.ExerciseRepository;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.domain.SessionDomainErrors;
import com.standofit.back.modules.training.execution.domain.SessionDomainException;
import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.query.SessionReadViewUpdater;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class AddExerciseLogHandler implements VoidCommandHandler<AddExerciseLogCommand> {

  private final SessionRepository repository;
  private final SessionReadViewUpdater readViewUpdater;
  private final ApplicationEventBus eventBus;
  private final ExerciseRepository exerciseRepository;

  public AddExerciseLogHandler(
      SessionRepository repository,
      SessionReadViewUpdater readViewUpdater,
      ApplicationEventBus eventBus,
      ExerciseRepository exerciseRepository) {
    this.repository = repository;
    this.readViewUpdater = readViewUpdater;
    this.eventBus = eventBus;
    this.exerciseRepository = exerciseRepository;
  }

  @Override
  public Class<AddExerciseLogCommand> commandType() {
    return AddExerciseLogCommand.class;
  }

  @Override
  public void execute(AddExerciseLogCommand command) {
    try {
      if (!exerciseRepository.existsById(command.exerciseId().value().toString())) {
        throw new SessionDomainException(SessionDomainErrors.EXERCISE_NOT_FOUND.getMessage());
      }
      Session session = repository.getById(command.sessionId());
      ExerciseLog newLog =
          ExerciseLog.create(
              command.logId(),
              command.exerciseId(),
              command.sets(),
              command.reps(),
              command.weight());
      Session updatedSession = session.addLog(newLog);
      Session saved = repository.save(updatedSession);
      readViewUpdater.upsert(saved);
      eventBus.publish(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_EXERCISE_ADDED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_ADDED.getDefaultDescription()));
    } catch (Exception e) {
      eventBus.publish(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_EXERCISE_ADDED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_ADDED.getDefaultDescription(),
              resolve(e)));
      throw e;
    }
  }
}
