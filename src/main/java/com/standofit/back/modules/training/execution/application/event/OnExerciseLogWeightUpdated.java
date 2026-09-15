package com.standofit.back.modules.training.execution.application.event;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.event.ExerciseLogWeightUpdated;
import com.standofit.back.modules.training.execution.infrastructure.query.SessionReadViewUpdater;
import com.standofit.back.shared.domain.bus.event.DomainEventHandler;
import com.standofit.back.shared.domain.valueobjects.Id;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OnExerciseLogWeightUpdated implements DomainEventHandler<ExerciseLogWeightUpdated> {

  private final SessionRepository repository;
  private final SessionReadViewUpdater readViewUpdater;

  public OnExerciseLogWeightUpdated(
      SessionRepository repository, SessionReadViewUpdater readViewUpdater) {
    this.repository = repository;
    this.readViewUpdater = readViewUpdater;
  }

  @Override
  public Class<ExerciseLogWeightUpdated> eventType() {
    return ExerciseLogWeightUpdated.class;
  }

  @Override
  @Transactional
  public void handle(ExerciseLogWeightUpdated event) {
    Id aggregateId = event.aggregateId();
    Session session = repository.getById(new SessionId(aggregateId.value()));
    readViewUpdater.upsert(session);
  }
}
