package com.standofit.back.modules.training.execution.application.query.get_session_by_id;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import org.springframework.stereotype.Service;

@Service
public class GetSessionByIdService extends ExecutionUseCase {

  public GetSessionByIdService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public SessionDto findById(GetSessionByIdQuery query) {
    try {
      Session session = repository.getById(query.sessionId());
      SessionDto dto = mapper.toDto(session);
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSION_QUERIED,
              query.sessionId().value().toString(),
              ExecutionActivityType.SESSION_QUERIED.getDefaultDescription()));
      return dto;
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSION_QUERIED,
              query.sessionId().value().toString(),
              ExecutionActivityType.SESSION_QUERIED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
