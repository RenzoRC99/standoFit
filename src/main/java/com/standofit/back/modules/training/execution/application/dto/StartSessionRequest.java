package com.standofit.back.modules.training.execution.application.dto;

import java.util.UUID;

/** Request DTO for starting a session. */
public record StartSessionRequest(UUID dayId) {}
