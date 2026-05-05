package com.standofit.back.modules.training.planning.presentation.controller;

import com.standofit.back.api.planning.ApiApi;
import com.standofit.back.api.planning.dto.*;
import com.standofit.back.modules.training.planning.application.dto.WorkoutDto;
import com.standofit.back.modules.training.planning.application.query.search_workouts.SearchWorkoutsQuery;
import com.standofit.back.modules.training.planning.presentation.mapper.WorkoutCommandMapper;
import com.standofit.back.modules.training.planning.presentation.mapper.WorkoutDTOMapper;
import com.standofit.back.modules.training.planning.presentation.mapper.WorkoutQueryMapper;
import com.standofit.back.shared.domain.bus.command.CommandBus;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.criteria.Criteria;
import com.standofit.back.shared.domain.criteria.PagedResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class WorkoutController implements ApiApi {

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final WorkoutDTOMapper workoutDTOMapper;
    private final WorkoutCommandMapper commandMapper;
    private final WorkoutQueryMapper queryMapper;

    public WorkoutController(CommandBus commandBus, QueryBus queryBus,
                             WorkoutDTOMapper workoutDTOMapper,
                             WorkoutCommandMapper commandMapper,
                             WorkoutQueryMapper queryMapper) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
        this.workoutDTOMapper = workoutDTOMapper;
        this.commandMapper = commandMapper;
        this.queryMapper = queryMapper;
    }

    @Override
    public ResponseEntity<UUID> addDayToWorkout(UUID workoutId, @RequestBody AddDayRequest addDayRequest) {
        var command = commandMapper.toAddDayToWorkoutCommand(workoutId, addDayRequest);
        commandBus.dispatch(command);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(workoutId);
    }

    @Override
    public ResponseEntity<Void> archiveWorkout(UUID workoutId) {
        var command = commandMapper.toArchiveWorkoutCommand(workoutId);
        commandBus.dispatch(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> changeWorkoutDescription(UUID workoutId, @RequestBody DescriptionRequest descriptionRequest) {
        var command = commandMapper.toChangeWorkoutDescriptionCommand(workoutId, descriptionRequest);
        commandBus.dispatch(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteWorkout(UUID workoutId) {
        var command = commandMapper.toDeleteWorkoutCommand(workoutId);
        commandBus.dispatch(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<UUID> duplicateWorkout(UUID workoutId, @RequestBody DuplicateWorkoutRequest duplicateWorkoutRequest) {
        var command = commandMapper.toDuplicateWorkoutCommand(workoutId, duplicateWorkoutRequest);
        UUID newWorkoutId = commandBus.dispatch(command);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(newWorkoutId);
    }

    @Override
    public ResponseEntity<WorkoutPageDTO> getAllWorkouts() {
        Criteria criteria = queryMapper.toGetAllWorkoutsCriteria();
        PagedResult<WorkoutDto> result = queryBus.ask(new SearchWorkoutsQuery(criteria));
        return ResponseEntity.ok(workoutDTOMapper.toPageDTO(result));
    }

    @Override
    public ResponseEntity<WorkoutDTO> getWorkoutById(UUID workoutId) {
        try {
            var query = queryMapper.toGetWorkoutByIdQuery(workoutId);
            WorkoutDto workout = queryBus.ask(query);
            return ResponseEntity.ok(workoutDTOMapper.toDTO(workout));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<UUID> planWorkout(@RequestBody PlanWorkoutRequest planWorkoutRequest) {
        var command = commandMapper.toPlanWorkoutCommand(planWorkoutRequest);
        UUID workoutId = commandBus.dispatch(command);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(workoutId);
    }

    @Override
    public ResponseEntity<Void> removeDayFromWorkout(UUID workoutId, UUID dayId) {
        var command = commandMapper.toRemoveDayFromWorkoutCommand(workoutId, dayId);
        commandBus.dispatch(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> renameWorkout(UUID workoutId, @RequestBody RenameRequest renameRequest) {
        var command = commandMapper.toRenameWorkoutCommand(workoutId, renameRequest);
        commandBus.dispatch(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> reorderDays(UUID workoutId, @RequestBody ReorderDaysRequest reorderDaysRequest) {
        var command = commandMapper.toReorderDaysCommand(workoutId, reorderDaysRequest);
        commandBus.dispatch(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<WorkoutPageDTO> searchWorkouts(@RequestBody SearchWorkoutsRequest searchWorkoutsRequest) {
        var query = queryMapper.toSearchWorkoutsQuery(searchWorkoutsRequest);
        PagedResult<WorkoutDto> result = queryBus.ask(query);
        return ResponseEntity.ok(workoutDTOMapper.toPageDTO(result));
    }
}