package com.standofit.back.modules.training.execution.application.query.search_sessions;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.application.query.SessionReadRepository;
import com.standofit.back.modules.training.execution.infrastructure.SessionNotFoundException;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SearchSessionsHandler implements QueryHandler<SearchSessionsQuery, SessionListDto> {

  private final SessionReadRepository readRepository;

  public SearchSessionsHandler(SessionReadRepository readRepository) {
    this.readRepository = readRepository;
  }

  @Override
  public Class<SearchSessionsQuery> queryType() {
    return SearchSessionsQuery.class;
  }

  @Override
  public SessionListDto handle(SearchSessionsQuery query) {
    if (query.sessionId() != null) {
      SessionDto dto =
          readRepository
              .findDtoById(query.sessionId().value())
              .orElseThrow(
                  () -> new SessionNotFoundException(query.sessionId().value().toString()));
      return new SessionListDto(List.of(dto));
    }
    return readRepository.findAllAsDtos();
  }
}
