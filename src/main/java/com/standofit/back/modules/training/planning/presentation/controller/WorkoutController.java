package com.standofit.back.modules.training.planning.presentation.controller;

import com.standofit.back.configuration.bus.ApplicationBusFacade;
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
import com.standofit.back.modules.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
import com.standofit.back.modules.training.planning.application.query.search_workouts.SearchWorkoutsQuery;
import com.standofit.back.modules.training.planning.presentation.dto.AddDayRequest;
import com.standofit.back.modules.training.planning.presentation.dto.DescriptionRequest;
import com.standofit.back.modules.training.planning.presentation.dto.DuplicateWorkoutRequest;
import com.standofit.back.modules.training.planning.presentation.dto.PlanWorkoutRequest;
import com.standofit.back.modules.training.planning.presentation.dto.ReorderDaysRequest;
import com.standofit.back.modules.training.planning.presentation.dto.RenameRequest;
import com.standofit.back.modules.training.planning.presentation.dto.SearchWorkoutsRequest;
import com.standofit.back.modules.training.planning.presentation.mapper.PlanWorkoutCommandMapper;
import com.standofit.back.modules.training.planning.presentation.mapper.SearchWorkoutsRequestMapper;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final ApplicationBusFacade bus;

    private final PlanWorkoutCommandMapper commandMapper;
    private final SearchWorkoutsRequestMapper searchMapper;

    public WorkoutController(
            ApplicationBusFacade bus,
            PlanWorkoutCommandMapper commandMapper,
            SearchWorkoutsRequestMapper searchMapper) {
        this.bus = bus;
        this.commandMapper = commandMapper;
        this.searchMapper = searchMapper;
    }

    @GetMapping
    public PagedResult<WorkoutDto> getAll() {
        return bus.ask(new SearchWorkoutsQuery(Criteria.empty()));
    }

    @PostMapping("/search")
    public PagedResult<WorkoutDto> search(@RequestBody SearchWorkoutsRequest request) {
        var criteria = searchMapper.toCriteria(request);
        return bus.ask(new SearchWorkoutsQuery(criteria));
    }

    @GetMapping("/{id}")
    public WorkoutDto getById(@PathVariable UUID id) {
        try {
            return bus.ask(new GetWorkoutByIdQuery(id));
        } catch (IllegalArgumentException e) {
            throw new WorkoutNotFoundException(id);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID plan(@RequestBody PlanWorkoutRequest request) {
        PlanWorkoutCommand command = commandMapper.toCommand(request);
        return bus.execute(command);
    }

    @PutMapping("/{id}/name")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rename(@PathVariable UUID id, @RequestBody RenameRequest request) {
        bus.execute(new RenameWorkoutCommand(id, request.name()));
    }

    @PutMapping("/{id}/description")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeDescription(@PathVariable UUID id, @RequestBody DescriptionRequest request) {
        bus.execute(new ChangeWorkoutDescriptionCommand(id, request.description()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        bus.execute(new DeleteWorkoutCommand(id));
    }

    @PostMapping("/{id}/days")
    @ResponseStatus(HttpStatus.CREATED)
    public void addDay(@PathVariable UUID id, @RequestBody AddDayRequest request) {
        var command = new AddDayToWorkoutCommand(
                id,
                request.dayName(),
                request.exercises().stream()
                        .map(ex -> new AddDayToWorkoutCommand.ExerciseInput(
                                ex.exerciseId(),
                                ex.sets(),
                                ex.reps(),
                                ex.restSeconds()))
                        .toList());
        bus.execute(command);
    }

    @DeleteMapping("/{id}/days/{dayId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeDay(@PathVariable UUID id, @PathVariable UUID dayId) {
        bus.execute(new RemoveDayFromWorkoutCommand(id, dayId));
    }

    @PutMapping("/{id}/days/reorder")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reorderDays(@PathVariable UUID id, @RequestBody ReorderDaysRequest request) {
        bus.execute(new ReorderDaysCommand(id, request.dayIds()));
    }

    @PostMapping("/{id}/duplicate")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID duplicate(@PathVariable UUID id, @RequestBody DuplicateWorkoutRequest request) {
        return bus.execute(new DuplicateWorkoutCommand(id, request.newName()));
    }

    @PostMapping("/{id}/archive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void archive(@PathVariable UUID id) {
        bus.execute(new ArchiveWorkoutCommand(id));
    }

    @ExceptionHandler(WorkoutNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(WorkoutNotFoundException e) {
    }

    public static class WorkoutNotFoundException extends RuntimeException {
        public WorkoutNotFoundException(UUID id) {
            super("Workout not found: " + id);
        }
    }
}
