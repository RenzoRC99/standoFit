package com.standofit.back.modules.training.execution.application.command.start_session;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class StartSessionService extends ExecutionUseCase {

  public StartSessionService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public UUID startSession(StartSessionCommand command) {
    UUID id = UUID.randomUUID();
    try {
      Session session = Session.create(new SessionId(id), command.dayId());
      repository.save(session);
      publishEvent(command.toSuccessEvent());
      return id;
    } catch (Exception e) {
      publishEvent(command.toFailureEvent(resolveErrorDetail(e)));
      throw e;
    }
  }
}
