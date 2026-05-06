package com.standofit.back.modules.training.planning.presentation;

import com.standofit.back.api.planning.PlanningApi;
import com.standofit.back.api.planning.dto.*;
import com.standofit.back.configuration.bus.ApplicationBus;
import com.standofit.back.modules.training.planning.application.command.add_day_to_workout.AddDayToWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.archive_workout.ArchiveWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.change_workout_description.ChangeWorkoutDescriptionCommand;
import com.standofit.back.modules.training.planning.application.command.delete_workout.DeleteWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.duplicate_workout.DuplicateWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.plan_workout.PlanWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.remove_day_from_workout.RemoveDayFromWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.rename_workout.RenameWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.reorder_days.ReorderDaysCommand;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.dto.WorkoutExerciseDto;
import com.standofit.back.modules.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
import com.standofit.back.modules.training.planning.application.query.search_workouts.SearchWorkoutsQuery;
import com.standofit.back.shared.domain.criteria.PagedResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class PlanningApiController implements PlanningApi {

    private final ApplicationBus bus;

    public PlanningApiController(ApplicationBus bus) {
        this.bus = bus;
    }

    @Override
    public ResponseEntity<UUID> addDayToWorkout(UUID workoutId, AddDayRequest request) {
        List<AddDayToWorkoutCommand.ExerciseInput> exercises = request.getExercises().stream()
                .map(e -> new AddDayToWorkoutCommand.ExerciseInput(
                        e.getExerciseId(),
                        e.getSets(),
                        e.getReps(),
                        e.getRestSeconds()))
                .toList();

        bus.execute(new AddDayToWorkoutCommand(workoutId, request.getDayName(), exercises));
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutId);
    }

    @Override
    public ResponseEntity<Void> archiveWorkout(UUID workoutId) {
        bus.execute(new ArchiveWorkoutCommand(workoutId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> changeWorkoutDescription(UUID workoutId, DescriptionRequest request) {
        bus.execute(new ChangeWorkoutDescriptionCommand(workoutId, request.getDescription()));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteWorkout(UUID workoutId) {
        bus.execute(new DeleteWorkoutCommand(workoutId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UUID> duplicateWorkout(UUID workoutId, DuplicateWorkoutRequest request) {
        UUID newWorkoutId = bus.execute(new DuplicateWorkoutCommand(workoutId, request.getNewName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(newWorkoutId);
    }

    @Override
    public ResponseEntity<WorkoutPageDTO> getAllWorkouts() {
        // TODO: Refactor to pass SearchWorkoutsRequest directly to SearchWorkoutsQuery
        var result = bus.ask(new SearchWorkoutsQuery("createdAt", "DESC", 0, 10, null));
        return ResponseEntity.ok(toApiWorkoutPageDTO(result));
    }

    @Override
    public ResponseEntity<WorkoutPageDTO> searchWorkouts(SearchWorkoutsRequest request) {
        // TODO: Refactor - pass request directly to SearchWorkoutsQuery (move conversion to handler)
        var filters = request.getFilters() != null
                ? request.getFilters().stream()
                  .map(f -> f.getField() + ":" + f.getOperator() + ":" + f.getValue())
                  .toList()
                : null;

        var result = bus.ask(new SearchWorkoutsQuery(
                request.getOrderBy(),
                request.getOrder().getValue(),
                request.getPage(),
                request.getPageSize(),
                filters
        ));
        return ResponseEntity.ok(toApiWorkoutPageDTO(result));
    }

    @Override
    public ResponseEntity<WorkoutDTO> getWorkoutById(UUID workoutId) {
        try {
            WorkoutDto dto = bus.ask(new GetWorkoutByIdQuery(workoutId));
            return ResponseEntity.ok(toApiWorkoutDTO(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UUID> planWorkout(PlanWorkoutRequest request) {
        List<PlanWorkoutCommand.DayInput> days = request.getDays().stream()
                .map(day -> new PlanWorkoutCommand.DayInput(
                        day.getName(),
                        day.getExercises().stream()
                                .map(ex -> new PlanWorkoutCommand.ExerciseInput(
                                        ex.getExerciseId(),
                                        ex.getSets(),
                                        ex.getReps(),
                                        ex.getRestSeconds()))
                                .toList()))
                .toList();

        UUID workoutId = bus.execute(new PlanWorkoutCommand(
                request.getName(),
                request.getDescription(),
                days
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(workoutId);
    }

    @Override
    public ResponseEntity<Void> removeDayFromWorkout(UUID workoutId, UUID dayId) {
        bus.execute(new RemoveDayFromWorkoutCommand(workoutId, dayId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> renameWorkout(UUID workoutId, RenameRequest request) {
        bus.execute(new RenameWorkoutCommand(workoutId, request.getName()));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> reorderDays(UUID workoutId, ReorderDaysRequest request) {
        bus.execute(new ReorderDaysCommand(workoutId, request.getDayIds()));
        return ResponseEntity.noContent().build();
    }

    private WorkoutDTO toApiWorkoutDTO(WorkoutDto dto) {
        return new WorkoutDTO()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .days(dto.days().stream().map(this::toApiWorkoutDayDTO).toList())
                .createdAt(dto.createdAt())
                .updatedAt(dto.updatedAt());
    }

    private WorkoutDayDTO toApiWorkoutDayDTO(
            com.standofit.back.modules.training.planning.application.dto.WorkoutDayDto dto
    ) {
        return new com.standofit.back.api.planning.dto.WorkoutDayDTO()
                .id(dto.id())
                .name(dto.name())
                .exercises(dto.exercises().stream().map(this::toApiWorkoutExerciseDTO).toList());
    }

    private WorkoutExerciseDTO toApiWorkoutExerciseDTO(
            WorkoutExerciseDto dto
    ) {
        return new com.standofit.back.api.planning.dto.WorkoutExerciseDTO()
                .id(dto.id())
                .exerciseId(dto.exerciseId())
                .sets(dto.sets())
                .reps(dto.reps())
                .restSeconds(dto.restSeconds());
    }

    private WorkoutPageDTO toApiWorkoutPageDTO(PagedResult<WorkoutDto> result) {
        return new WorkoutPageDTO()
                .content(result.items().stream().map(this::toApiWorkoutDTO).toList())
                .pageNumber(result.page())
                .pageSize(result.pageSize())
                .totalElements((int) result.total())
                .totalPages(result.totalPages());
    }
}
