package com.standofit.back.modules.training.execution.application.query.get_all_sessions;

import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetAllSessionsHandler implements QueryHandler<GetAllSessionsQuery, SessionListDto> {

  private final GetAllSessionsService service;

  public GetAllSessionsHandler(GetAllSessionsService service) {
    this.service = service;
  }

  @Override
  public Class<GetAllSessionsQuery> queryType() {
    return GetAllSessionsQuery.class;
  }

  @Override
  public SessionListDto handle(GetAllSessionsQuery query) {
    return service.findAll(query);
  }
}
