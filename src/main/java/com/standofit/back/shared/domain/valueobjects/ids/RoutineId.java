package com.standofit.back.shared.domain.valueobjects.ids;

import com.standofit.back.shared.domain.valueobjects.Id;

import java.util.UUID;

public final class RoutineId extends Id {
    RoutineId(UUID value) {
        super(value);
        validateNotNull();
    }
}
