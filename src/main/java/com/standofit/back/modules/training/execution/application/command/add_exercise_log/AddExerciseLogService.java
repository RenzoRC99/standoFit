package com.standofit.back.modules.training.execution.application.command.add_exercise_log;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
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
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_EXERCISE_ADDED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_ADDED.getDefaultDescription()));
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_EXERCISE_ADDED,
              command.sessionId().value().toString(),
              ExecutionActivityType.SESSION_EXERCISE_ADDED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
