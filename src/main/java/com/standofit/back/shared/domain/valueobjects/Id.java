package com.standofit.back.shared.domain.valueobjects;

import java.util.UUID;

public abstract class Id extends BaseVO<UUID> {
    protected Id(UUID value) {
        super(value);
        validateNotNull();
    }
}
