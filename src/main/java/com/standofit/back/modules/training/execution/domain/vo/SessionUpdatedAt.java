package com.standofit.back.modules.training.execution.domain.vo;

import com.standofit.back.shared.domain.valueobjects.DateTimeVO;
import java.time.Instant;

public class SessionUpdatedAt extends DateTimeVO {
    public SessionUpdatedAt(Instant value) {
        super(value);
    }
}