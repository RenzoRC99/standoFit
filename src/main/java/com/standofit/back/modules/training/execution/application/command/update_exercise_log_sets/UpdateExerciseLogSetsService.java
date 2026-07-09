package com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
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
      Session session = repository.getById(command.sessionId());
      Session updatedSession = session.updateLogSets(command.logId(), command.sets());
      repository.save(updatedSession);
      publishEvent(command.toSuccessEvent());
    } catch (Exception e) {
      publishEvent(command.toFailureEvent(resolveErrorDetail(e)));
      throw e;
    }
  }
}
