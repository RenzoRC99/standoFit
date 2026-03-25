package com.standofit.back.training.planning.domain;

import com.standofit.shared.domain.ids.RoutineId;
import com.standofit.shared.domain.ids.UserId;
import com.standofit.back.training.planning.domain.vo.RoutineNameVO;

public final class Routine {
    private final RoutineId id;
    private final RoutineNameVO name;
    private final UserId userId;

    private Routine(RoutineId id, RoutineNameVO name, UserId userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

}
