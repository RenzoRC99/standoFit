package com.standofit.back.modules.training.execution.application.command.remove_exercise_log;

import static com.standofit.back.modules.training.execution.application.ErrorDetailResolver.resolve;

import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class RemoveExerciseLogHandler implements VoidCommandHandler<RemoveExerciseLogCommand> {

  private final SessionRepository repository;
  private final ApplicationEventBus eventBus;

  public RemoveExerciseLogHandler(SessionRepository repository, ApplicationEventBus eventBus) {
    this.repository = repository;
    this.eventBus = eventBus;
  }

  @Override
  public Class<RemoveExerciseLogCommand> commandType() {
    return RemoveExerciseLogCommand.class;
  }

  @Override
  public void execute(RemoveExerciseLogCommand command) {
    try {
      Session session = repository.getById(command.sessionId());
      Session updatedSession = session.removeLog(command.logId());
      repository.save(updatedSession);
      eventBus.publish(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_EXERCISE_REMOVED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_REMOVED.getDefaultDescription()));
    } catch (Exception e) {
      eventBus.publish(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_EXERCISE_REMOVED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_REMOVED.getDefaultDescription(),
              resolve(e)));
      throw e;
    }
  }
}
