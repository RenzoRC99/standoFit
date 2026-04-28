package com.standofit.back.modules.training.execution.application.query.get_session_by_id;

import com.standofit.back.api.execution.dto.SessionDTO;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.infrastructure.mapper.SessionDTOMapper;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class GetSessionByIdHandler implements QueryHandler<GetSessionByIdQuery, SessionDTO> {

  private final SessionRepository repository;
  private final SessionDTOMapper mapper;

  public GetSessionByIdHandler(SessionRepository repository, SessionDTOMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Class<GetSessionByIdQuery> queryType() {
    return GetSessionByIdQuery.class;
  }

  @Override
  public SessionDTO handle(GetSessionByIdQuery query) {
    Session session = repository.findById(query.sessionId());
    if (session == null) {
      throw new IllegalArgumentException("Session not found: " + query.sessionId());
    }
    return mapper.toDTO(session);
  }
}
