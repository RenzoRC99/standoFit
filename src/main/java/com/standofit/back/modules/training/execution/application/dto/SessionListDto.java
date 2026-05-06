package com.standofit.back.modules.training.execution.application.dto;

import java.util.List;

/**
 * Data Transfer Object for Session list.
 */
public record SessionListDto(
    List<SessionDto> sessions) {}