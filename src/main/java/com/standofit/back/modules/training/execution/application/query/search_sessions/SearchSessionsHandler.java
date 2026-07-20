package com.standofit.back.modules.training.execution.application.query.search_sessions;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.query.SessionReadRepository;
import com.standofit.back.modules.training.execution.infrastructure.SessionNotFoundException;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;
import org.springframework.stereotype.Component;

@Component
public class SearchSessionsHandler
    implements QueryHandler<SearchSessionsQuery, PagedResult<SessionDto>> {

  private final SessionReadRepository readRepository;

  public SearchSessionsHandler(SessionReadRepository readRepository) {
    this.readRepository = readRepository;
  }

  @Override
  public Class<SearchSessionsQuery> queryType() {
    return SearchSessionsQuery.class;
  }

  @Override
  public PagedResult<SessionDto> handle(SearchSessionsQuery query) {
    var criteria =
        Criteria.fromFilterValues(
            query.orderBy(), query.order(), query.page(), query.pageSize(), query.filters());

    PagedResult<SessionDto> result = readRepository.searchByCriteria(criteria);

    boolean isByIdLookup =
        query.filters().stream()
            .anyMatch(
                f ->
                    "id".equals(f.get("field"))
                        && "=".equals(f.get("operator"))
                        && query.pageSize() == 1);

    if (isByIdLookup && result.items().isEmpty()) {
      throw new SessionNotFoundException(
          query.filters().stream()
              .filter(f -> "id".equals(f.get("field")))
              .findFirst()
              .map(f -> f.get("value"))
              .orElse("unknown"));
    }

    return result;
  }
}
