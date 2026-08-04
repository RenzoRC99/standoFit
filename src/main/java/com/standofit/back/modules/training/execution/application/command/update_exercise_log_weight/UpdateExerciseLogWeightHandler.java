package com.standofit.back.modules.training.execution.application.command.update_exercise_log_weight;

import static com.standofit.back.modules.training.execution.application.ErrorDetailResolver.resolve;

import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.command.VoidCommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateExerciseLogWeightHandler
    implements VoidCommandHandler<UpdateExerciseLogWeightCommand> {

  private final SessionRepository repository;
  private final ApplicationEventBus eventBus;

  public UpdateExerciseLogWeightHandler(
      SessionRepository repository, ApplicationEventBus eventBus) {
    this.repository = repository;
    this.eventBus = eventBus;
  }

  @Override
  public Class<UpdateExerciseLogWeightCommand> commandType() {
    return UpdateExerciseLogWeightCommand.class;
  }

  @Override
  public void execute(UpdateExerciseLogWeightCommand command) {
    try {
      Session session = repository.getById(command.sessionId());
      Session updatedSession = session.updateLogWeight(command.logId(), command.weight());
      repository.save(updatedSession);
      eventBus.publish(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_EXERCISE_WEIGHT_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_WEIGHT_UPDATED.getDefaultDescription()));
    } catch (Exception e) {
      eventBus.publish(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_EXERCISE_WEIGHT_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_WEIGHT_UPDATED.getDefaultDescription(),
              resolve(e)));
      throw e;
    }
  }
}
