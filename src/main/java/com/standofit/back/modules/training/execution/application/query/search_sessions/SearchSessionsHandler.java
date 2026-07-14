package com.standofit.back.modules.training.execution.application.query.search_sessions;

import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.application.query.SessionReadRepository;
import com.standofit.back.modules.training.execution.infrastructure.SessionNotFoundException;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.Filter;
import com.standofit.back.shared.domain.criteria.PagedResult;
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
    Criteria criteria = buildCriteria(query);
    PagedResult<com.standofit.back.modules.training.execution.application.dto.SessionDto> result =
        readRepository.searchByCriteria(criteria);
    if (query.sessionId() != null && result.items().isEmpty()) {
      throw new SessionNotFoundException(query.sessionId().value().toString());
    }
    return new SessionListDto(result.items());
  }

  private Criteria buildCriteria(SearchSessionsQuery query) {
    var builder = Criteria.builder().page(query.page(), query.pageSize()).desc("createdAt");

    if (query.sessionId() != null) {
      builder.filter(Filter.equal("id", query.sessionId().value()));
    }
    if (query.status() != null) {
      builder.filter(Filter.equal("status", query.status()));
    }

    return builder.build();
  }
}
