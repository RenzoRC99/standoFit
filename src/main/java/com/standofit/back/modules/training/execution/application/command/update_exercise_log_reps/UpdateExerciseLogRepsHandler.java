package com.standofit.back.modules.training.execution.application.command.update_exercise_log_reps;

import static com.standofit.back.modules.training.execution.application.ErrorDetailResolver.resolve;

import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.query.SessionReadViewUpdater;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateExerciseLogRepsHandler
    implements VoidCommandHandler<UpdateExerciseLogRepsCommand> {

  private final SessionRepository repository;
  private final SessionReadViewUpdater readViewUpdater;
  private final ApplicationEventBus eventBus;

  public UpdateExerciseLogRepsHandler(
      SessionRepository repository,
      SessionReadViewUpdater readViewUpdater,
      ApplicationEventBus eventBus) {
    this.repository = repository;
    this.readViewUpdater = readViewUpdater;
    this.eventBus = eventBus;
  }

  @Override
  public Class<UpdateExerciseLogRepsCommand> commandType() {
    return UpdateExerciseLogRepsCommand.class;
  }

  @Override
  public void execute(UpdateExerciseLogRepsCommand command) {
    try {
      Session session = repository.getById(command.sessionId());
      Session updatedSession = session.updateLogReps(command.logId(), command.reps());
      Session saved = repository.save(updatedSession);
      readViewUpdater.upsert(saved);
      eventBus.publish(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_EXERCISE_REPS_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_REPS_UPDATED.getDefaultDescription()));
    } catch (Exception e) {
      eventBus.publish(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_EXERCISE_REPS_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_REPS_UPDATED.getDefaultDescription(),
              resolve(e)));
      throw e;
    }
  }
}
