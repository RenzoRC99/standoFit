package com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets;

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
public class UpdateExerciseLogSetsService extends ExecutionUseCase {

  public UpdateExerciseLogSetsService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void updateSets(UpdateExerciseLogSetsCommand command) {
    try {
      Session session = repository.findById(command.sessionId());
      Session updatedSession = session.updateLogSets(command.logId(), command.sets());
      repository.save(updatedSession);
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_EXERCISE_SETS_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_SETS_UPDATED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_EXERCISE_SETS_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_SETS_UPDATED.getDefaultDescription(),
              e.getMessage()));
      throw new ApplicationExecutionException(
          ExecutionApplicationError.SESSION_EXERCISE_SETS_UPDATE_FAILED,
          command.sessionId().value().toString(),
          e);
    }
  }
}
