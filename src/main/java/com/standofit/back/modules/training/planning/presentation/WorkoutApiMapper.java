package com.standofit.back.modules.training.planning.presentation;

import com.standofit.back.api.planning.dto.WorkoutDTO;
import com.standofit.back.api.planning.dto.WorkoutDayDTO;
import com.standofit.back.api.planning.dto.WorkoutExerciseDTO;
import com.standofit.back.api.planning.dto.WorkoutPageDTO;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDayDto;
import com.standofit.back.modules.training.planning.application.dto.WorkoutExerciseDto;
import com.standofit.back.shared.domain.criteria.PagedResult;

public class WorkoutApiMapper {

  public static WorkoutDTO toApi(WorkoutDto dto) {
    return new WorkoutDTO()
        .id(dto.id())
        .name(dto.name())
        .description(dto.description())
        .days(dto.days().stream().map(WorkoutApiMapper::toApi).toList())
        .createdAt(dto.createdAt())
        .updatedAt(dto.updatedAt());
  }

  public static WorkoutDayDTO toApi(WorkoutDayDto dto) {
    return new WorkoutDayDTO()
        .id(dto.id())
        .name(dto.name())
        .exercises(dto.exercises().stream().map(WorkoutApiMapper::toApi).toList());
  }

  public static WorkoutExerciseDTO toApi(WorkoutExerciseDto dto) {
    return new WorkoutExerciseDTO()
        .id(dto.id())
        .exerciseId(dto.exerciseId())
        .sets(dto.sets())
        .reps(dto.reps())
        .restSeconds(dto.restSeconds());
  }

  public static WorkoutPageDTO toApi(PagedResult<WorkoutDto> result) {
    return new WorkoutPageDTO()
        .content(result.items().stream().map(WorkoutApiMapper::toApi).toList())
        .pageNumber(result.page())
        .pageSize(result.pageSize())
        .totalElements((int) result.total())
        .totalPages(result.totalPages());
  }
}