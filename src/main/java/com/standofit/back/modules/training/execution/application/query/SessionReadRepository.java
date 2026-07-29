package com.standofit.back.modules.training.execution.application.query;

import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;

public interface SessionReadRepository {

  PagedResult<SessionDto> searchByCriteria(Criteria criteria);
}
