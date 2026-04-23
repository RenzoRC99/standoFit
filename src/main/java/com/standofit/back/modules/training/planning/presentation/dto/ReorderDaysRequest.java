package com.standofit.back.modules.training.planning.presentation.dto;

import java.util.List;
import java.util.UUID;

public record ReorderDaysRequest(List<UUID> dayIds) {
}