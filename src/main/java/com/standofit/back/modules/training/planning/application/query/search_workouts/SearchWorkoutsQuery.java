package com.standofit.back.modules.training.planning.application.query.search_workouts;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;

public record SearchWorkoutsQuery(Criteria criteria) implements Query<PagedResult<WorkoutDto>> {
}
