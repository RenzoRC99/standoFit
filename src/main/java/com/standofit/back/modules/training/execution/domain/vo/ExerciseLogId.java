package com.standofit.back.modules.training.execution.domain.vo;

import com.standofit.back.shared.domain.valueobjects.Id;
import java.util.UUID;

public class ExerciseLogId extends Id {
    public ExerciseLogId(UUID value) {
        super(value);
    }
}