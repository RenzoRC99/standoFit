package com.standofit.back.modules.training.execution.application.query.get_all_sessions;

import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionDTOMapper;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAllSessionsHandler implements QueryHandler<GetAllSessionsQuery, SessionListDto> {

  private final SessionRepository repository;
  private final SessionDTOMapper mapper;

  public GetAllSessionsHandler(SessionRepository repository, SessionDTOMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Class<GetAllSessionsQuery> queryType() {
    return GetAllSessionsQuery.class;
  }

  @Override
  public SessionListDto handle(GetAllSessionsQuery query) {
    List<Session> sessions = repository.findAll();
    return mapper.toListDTO(sessions);
  }
}
