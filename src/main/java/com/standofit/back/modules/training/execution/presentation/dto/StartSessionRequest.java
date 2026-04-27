package com.standofit.back.modules.training.execution.presentation.dto;

import java.util.UUID;

public record StartSessionRequest(
        UUID dayId
) {
}