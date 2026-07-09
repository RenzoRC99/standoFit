package com.standofit.back.modules.training.execution.application.query.get_all_sessions;

import com.standofit.back.modules.training.execution.application.ExecutionUseCase;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.application.event.ExecutionActivityType;
import com.standofit.back.modules.training.execution.application.event.SessionActivityEvent;
import com.standofit.back.modules.training.execution.application.mapper.SessionDtoMapper;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAllSessionsService extends ExecutionUseCase {

  public GetAllSessionsService(
      SessionRepository repository,
      SessionDtoMapper mapper,
      ApplicationEventBus applicationEventBus) {
    super(repository, mapper, applicationEventBus);
  }

  public SessionListDto findAll(GetAllSessionsQuery query) {
    try {
      List<Session> sessions = repository.findAll();
      SessionListDto dto = mapper.toListDto(sessions);
      publishEvent(
          SessionActivityEvent.success(
              ExecutionActivityType.SESSIONS_SEARCHED,
              "all",
              ExecutionActivityType.SESSIONS_SEARCHED.getDefaultDescription()));
      return dto;
    } catch (Exception e) {
      publishEvent(
          SessionActivityEvent.failure(
              ExecutionActivityType.SESSIONS_SEARCHED,
              "all",
              ExecutionActivityType.SESSIONS_SEARCHED.getDefaultDescription(),
              resolveErrorDetail(e)));
      throw e;
    }
  }
}
