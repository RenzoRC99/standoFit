package com.standofit.back.modules.training.planning.presentation.mapper;

import com.standofit.back.api.planning.dto.*;
import com.standofit.back.modules.training.planning.application.command.add_day_to_workout.AddDayToWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.archive_workout.ArchiveWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.change_workout_description.ChangeWorkoutDescriptionCommand;
import com.standofit.back.modules.training.planning.application.command.delete_workout.DeleteWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.duplicate_workout.DuplicateWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.plan_workout.PlanWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.remove_day_from_workout.RemoveDayFromWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.rename_workout.RenameWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.reorder_days.ReorderDaysCommand;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class WorkoutCommandMapper {

    public PlanWorkoutCommand toPlanWorkoutCommand(PlanWorkoutRequest request) {
        List<PlanWorkoutCommand.DayInput> days = request.getDays().stream()
                .map(this::toDayInput)
                .collect(Collectors.toList());

        return new PlanWorkoutCommand(
                request.getName(),
                request.getDescription(),
                days
        );
    }

    private PlanWorkoutCommand.DayInput toDayInput(DayInputDTO dto) {
        List<PlanWorkoutCommand.ExerciseInput> exercises = dto.getExercises().stream()
                .map(this::toExerciseInput)
                .collect(Collectors.toList());

        return new PlanWorkoutCommand.DayInput(dto.getName(), exercises);
    }

    private PlanWorkoutCommand.ExerciseInput toExerciseInput(ExerciseInputDTO dto) {
        return new PlanWorkoutCommand.ExerciseInput(
                dto.getExerciseId(),
                dto.getSets(),
                dto.getReps(),
                dto.getRestSeconds()
        );
    }

    public AddDayToWorkoutCommand toAddDayToWorkoutCommand(UUID workoutId, AddDayRequest request) {
        List<AddDayToWorkoutCommand.ExerciseInput> exercises = request.getExercises().stream()
                .map(e -> new AddDayToWorkoutCommand.ExerciseInput(
                        e.getExerciseId(),
                        e.getSets(),
                        e.getReps(),
                        e.getRestSeconds()))
                .collect(Collectors.toList());

        return new AddDayToWorkoutCommand(workoutId, request.getDayName(), exercises);
    }

    public ArchiveWorkoutCommand toArchiveWorkoutCommand(UUID workoutId) {
        return new ArchiveWorkoutCommand(workoutId);
    }

    public ChangeWorkoutDescriptionCommand toChangeWorkoutDescriptionCommand(UUID workoutId, DescriptionRequest request) {
        return new ChangeWorkoutDescriptionCommand(workoutId, request.getDescription());
    }

    public DeleteWorkoutCommand toDeleteWorkoutCommand(UUID workoutId) {
        return new DeleteWorkoutCommand(workoutId);
    }

    public DuplicateWorkoutCommand toDuplicateWorkoutCommand(UUID workoutId, DuplicateWorkoutRequest request) {
        return new DuplicateWorkoutCommand(workoutId, request.getNewName());
    }

    public RemoveDayFromWorkoutCommand toRemoveDayFromWorkoutCommand(UUID workoutId, UUID dayId) {
        return new RemoveDayFromWorkoutCommand(workoutId, dayId);
    }

    public RenameWorkoutCommand toRenameWorkoutCommand(UUID workoutId, RenameRequest request) {
        return new RenameWorkoutCommand(workoutId, request.getName());
    }

    public ReorderDaysCommand toReorderDaysCommand(UUID workoutId, ReorderDaysRequest request) {
        return new ReorderDaysCommand(workoutId, request.getDayIds());
    }
}