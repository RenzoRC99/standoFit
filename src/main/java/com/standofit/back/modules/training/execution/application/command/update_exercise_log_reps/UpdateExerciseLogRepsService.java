package com.standofit.back.modules.training.execution.application.command.update_exercise_log_reps;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class UpdateExerciseLogRepsService extends ExecutionUseCase {

  public UpdateExerciseLogRepsService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void updateReps(UpdateExerciseLogRepsCommand command) {
    try {
      Session session = repository.findById(command.sessionId());
      Session updatedSession = session.updateLogReps(command.logId(), command.reps());
      repository.save(updatedSession);
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_EXERCISE_REPS_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_REPS_UPDATED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_EXERCISE_REPS_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_REPS_UPDATED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
