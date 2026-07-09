package com.standofit.back.modules.training.execution.application.command.add_exercise_log;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class AddExerciseLogService extends ExecutionUseCase {
  public AddExerciseLogService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public void addExerciseLog(AddExerciseLogCommand command) {
    try {
      Session session = repository.getById(command.sessionId());
      ExerciseLog newLog =
          ExerciseLog.create(
              command.logId(),
              command.exerciseId(),
              command.sets(),
              command.reps(),
              command.weight());
      Session updatedSession = session.addLog(newLog);
      repository.save(updatedSession);
      publishEvent(command.toSuccessEvent());
    } catch (Exception e) {
      publishEvent(command.toFailureEvent(resolveErrorDetail(e)));
      throw e;
    }
  }
}
