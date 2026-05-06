package com.standofit.back.modules.training.planning.application.query.search_workouts;

import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.criteria.PagedResult;
import java.util.List;

public record SearchWorkoutsQuery(
    String orderBy,
    String order,
    int page,
    int pageSize,
    List<String> filters
) implements Query<PagedResult<WorkoutDto>> {}