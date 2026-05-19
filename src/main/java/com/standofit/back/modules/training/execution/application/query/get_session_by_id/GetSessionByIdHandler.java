package com.standofit.back.modules.training.execution.application.query.get_session_by_id;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetSessionByIdHandler implements QueryHandler<GetSessionByIdQuery, SessionDto> {

  private final GetSessionByIdService service;

  public GetSessionByIdHandler(GetSessionByIdService service) {
    this.service = service;
  }

  @Override
  public Class<GetSessionByIdQuery> queryType() {
    return GetSessionByIdQuery.class;
  }

  @Override
  public SessionDto handle(GetSessionByIdQuery query) {
    return service.findById(query);
  }
}
