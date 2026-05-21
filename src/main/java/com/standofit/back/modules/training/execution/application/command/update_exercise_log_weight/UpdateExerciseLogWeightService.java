package com.standofit.back.modules.training.execution.application.command.update_exercise_log_weight;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
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
      publishEvent(command.toSuccessEvent());
    } catch (Exception e) {
      publishEvent(command.toFailureEvent(resolveErrorDetail(e)));
      throw e;
    }
  }
}
