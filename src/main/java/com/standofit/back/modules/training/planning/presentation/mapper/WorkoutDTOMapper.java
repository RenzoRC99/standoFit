package com.standofit.back.modules.training.planning.presentation.mapper;

import com.standofit.back.api.planning.dto.WorkoutDTO;
import com.standofit.back.api.planning.dto.WorkoutDayDTO;
import com.standofit.back.api.planning.dto.WorkoutExerciseDTO;
import com.standofit.back.api.planning.dto.WorkoutPageDTO;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDayDto;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.dto.WorkoutExerciseDto;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class WorkoutDTOMapper {

    public WorkoutPageDTO toPageDTO(
            com.standofit.back.shared.domain.criteria.PagedResult<WorkoutDto> result) {
        WorkoutPageDTO page = new WorkoutPageDTO();
        page.setContent(
                result.items().stream().map(this::toDTO).collect(Collectors.toList()));
        page.setTotalElements((int) result.total());
        page.setTotalPages(result.totalPages());
        page.setPageNumber(result.page());
        page.setPageSize(result.pageSize());
        return page;
    }

    public WorkoutDTO toDTO(WorkoutDto dto) {
        WorkoutDTO workout = new WorkoutDTO();
        workout.setId(dto.id());
        workout.setName(dto.name());
        workout.setDescription(dto.description());
        workout.setArchived(false);
        if (dto.createdAt() != null) {
            workout.setCreatedAt(
                    OffsetDateTime.parse(dto.createdAt(), DateTimeFormatter.ISO_DATE_TIME));
        }
        if (dto.updatedAt() != null) {
            workout.setUpdatedAt(
                    OffsetDateTime.parse(dto.updatedAt(), DateTimeFormatter.ISO_DATE_TIME));
        }
        if (dto.days() != null) {
            workout.setDays(
                    dto.days().stream().map(this::toDayDTO).collect(Collectors.toList()));
        }
        return workout;
    }

    private WorkoutDayDTO toDayDTO(WorkoutDayDto dayDto) {
        WorkoutDayDTO day =
                new WorkoutDayDTO();
        day.setId(dayDto.id());
        day.setName(dayDto.name());
        if (dayDto.exercises() != null) {
            day.setExercises(
                    dayDto.exercises().stream()
                            .map(this::toExerciseDTO)
                            .collect(Collectors.toList()));
        }
        return day;
    }

    private WorkoutExerciseDTO toExerciseDTO(
            WorkoutExerciseDto exDto) {
        WorkoutExerciseDTO ex =
                new WorkoutExerciseDTO();
        ex.setId(exDto.id());
        ex.setExerciseId(exDto.exerciseId());
        ex.setSets(exDto.sets());
        ex.setReps(exDto.reps());
        ex.setRestSeconds(exDto.restSeconds());
        return ex;
    }
}
