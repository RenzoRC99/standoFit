package com.standofit.back.modules.training.execution.application.command.update_exercise_log_weight;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class UpdateExerciseLogWeightService extends ExecutionUseCase {

  public UpdateExerciseLogWeightService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void updateWeight(UpdateExerciseLogWeightCommand command) {
    try {
      Session session = repository.getById(command.sessionId());
      Session updatedSession = session.updateLogWeight(command.logId(), command.weight());
      repository.save(updatedSession);
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_EXERCISE_WEIGHT_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_WEIGHT_UPDATED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_EXERCISE_WEIGHT_UPDATED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_WEIGHT_UPDATED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
