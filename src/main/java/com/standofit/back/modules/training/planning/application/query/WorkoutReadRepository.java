package com.standofit.back.modules.training.planning.application.query;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.Optional;

public interface WorkoutReadRepository {

  Optional<WorkoutDto> findById(WorkoutId id);

  PagedResult<WorkoutDto> search(Criteria criteria);
}
