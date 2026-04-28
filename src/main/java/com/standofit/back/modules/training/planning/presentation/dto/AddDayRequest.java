package com.standofit.back.modules.training.planning.presentation.dto;

import java.util.List;
import java.util.UUID;

public record AddDayRequest(String dayName, List<ExerciseInput> exercises) {
  public record ExerciseInput(UUID exerciseId, int sets, int reps, int restSeconds) {}
}
