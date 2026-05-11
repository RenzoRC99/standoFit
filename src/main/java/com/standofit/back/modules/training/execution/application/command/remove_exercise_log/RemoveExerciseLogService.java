package com.standofit.back.modules.training.execution.application.command.remove_exercise_log;

import com.standofit.back.modules.training.execution.application.ApplicationExecutionException;
import com.standofit.back.modules.training.execution.application.ExecutionApplicationError;
import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class RemoveExerciseLogService extends ExecutionUseCase {

  public RemoveExerciseLogService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void removeExerciseLog(RemoveExerciseLogCommand command) {
    try {
      Session session = repository.findById(command.sessionId());
      Session updatedSession = session.removeLog(command.logId());
      repository.save(updatedSession);
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_EXERCISE_REMOVED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_REMOVED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_EXERCISE_REMOVED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_REMOVED.getDefaultDescription(),
              e.getMessage()));
      throw new ApplicationExecutionException(
          ExecutionApplicationError.SESSION_EXERCISE_REMOVE_FAILED,
          command.sessionId().value().toString(),
          e);
    }
  }
}
