package com.standofit.back.modules.training.planning.presentation;

import com.standofit.back.api.planning.PlanningApi;
import com.standofit.back.api.planning.dto.*;
import com.standofit.back.modules.training.planning.application.command.change_workout_description.ChangeWorkoutDescriptionCommand;
import com.standofit.back.modules.training.planning.application.command.delete_workout.DeleteWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.duplicate_workout.DuplicateWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.remove_day_from_workout.RemoveDayFromWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.rename_day.RenameDayCommand;
import com.standofit.back.modules.training.planning.application.command.rename_workout.RenameWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.reorder_days.ReorderDaysCommand;
import com.standofit.back.modules.training.planning.application.command.replace_day_exercises.ReplaceDayExercisesCommand;
import com.standofit.back.modules.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
import com.standofit.back.modules.training.planning.infrastructure.WorkoutInfrastructureException;
import com.standofit.back.shared.domain.bus.command.CommandBus;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanningApiController implements PlanningApi {

  private final CommandBus commandBus;
  private final QueryBus queryBus;

  public PlanningApiController(CommandBus commandBus, QueryBus queryBus) {
    this.commandBus = commandBus;
    this.queryBus = queryBus;
  }

  @Override
  @Transactional
  public ResponseEntity<WorkoutCreatedResponse> addDayToWorkout(UUID workoutId, AddDayRequest request) {
    commandBus.dispatch(PlanningCommandMapper.toCommand(workoutId, request));
    return ResponseEntity.status(HttpStatus.CREATED).body(new WorkoutCreatedResponse(workoutId));
  }

  @Override
  @Transactional
  public ResponseEntity<Void> changeWorkoutDescription(UUID workoutId, DescriptionRequest request) {
    commandBus.dispatch(new ChangeWorkoutDescriptionCommand(new WorkoutId(workoutId), request.getDescription()));
    return ResponseEntity.noContent().build();
  }

  @Override
  @Transactional
  public ResponseEntity<Void> updateWorkout(UUID workoutId, UpdateWorkoutRequest request) {
    if (request.getName() != null)
      commandBus.dispatch(new RenameWorkoutCommand(new WorkoutId(workoutId), request.getName()));
    if (request.getDescription() != null)
      commandBus.dispatch(new ChangeWorkoutDescriptionCommand(new WorkoutId(workoutId), request.getDescription()));
    return ResponseEntity.noContent().build();
  }

  @Override
  @Transactional
  public ResponseEntity<Void> deleteWorkout(UUID workoutId) {
    commandBus.dispatch(new DeleteWorkoutCommand(new WorkoutId(workoutId)));
    return ResponseEntity.noContent().build();
  }

  @Override
  @Transactional
  public ResponseEntity<WorkoutCreatedResponse> duplicateWorkout(UUID workoutId, DuplicateWorkoutRequest request) {
    UUID newWorkoutId =
        commandBus.dispatch(new DuplicateWorkoutCommand(new WorkoutId(workoutId), request.getNewName()));
    return ResponseEntity.status(HttpStatus.CREATED).body(new WorkoutCreatedResponse(newWorkoutId));
  }

  @Override
  public ResponseEntity<WorkoutPageDTO> getAllWorkouts() {
    var result = queryBus.ask(PlanningQueryMapper.all());
    return ResponseEntity.ok(WorkoutApiMapper.toApi(result));
  }

  @Override
  public ResponseEntity<WorkoutPageDTO> searchWorkouts(SearchWorkoutsRequest request) {
    var result = queryBus.ask(PlanningQueryMapper.fromRequest(request));
    return ResponseEntity.ok(WorkoutApiMapper.toApi(result));
  }

  @Override
  public ResponseEntity<WorkoutDTO> getWorkoutById(UUID workoutId) {
    try {
      var dto = queryBus.ask(new GetWorkoutByIdQuery(new WorkoutId(workoutId)));
      return ResponseEntity.ok(WorkoutApiMapper.toApi(dto));
    } catch (WorkoutInfrastructureException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  @Transactional
  public ResponseEntity<WorkoutCreatedResponse> planWorkout(PlanWorkoutRequest request) {
    UUID workoutId = commandBus.dispatch(PlanningCommandMapper.toCreateCommand(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(new WorkoutCreatedResponse(workoutId));
  }

  @Override
  @Transactional
  public ResponseEntity<Void> updateWorkoutDay(UUID workoutId, UUID dayId, UpdateDayRequest request) {
    if (request.getName() != null)
      commandBus.dispatch(new RenameDayCommand(new WorkoutId(workoutId), new WorkoutDayId(dayId), request.getName()));
    if (request.getExercises() != null)
      commandBus.dispatch(new ReplaceDayExercisesCommand(new WorkoutId(workoutId), new WorkoutDayId(dayId),
          request.getExercises().stream()
              .map(e -> new ReplaceDayExercisesCommand.ExerciseInput(e.getExerciseId(), e.getSets(), e.getReps(), e.getRestSeconds()))
              .toList()));
    return ResponseEntity.noContent().build();
  }

  @Override
  @Transactional
  public ResponseEntity<Void> removeDayFromWorkout(UUID workoutId, UUID dayId) {
    commandBus.dispatch(new RemoveDayFromWorkoutCommand(new WorkoutId(workoutId), new WorkoutDayId(dayId)));
    return ResponseEntity.noContent().build();
  }

  @Override
  @Transactional
  public ResponseEntity<Void> renameWorkout(UUID workoutId, RenameRequest request) {
    commandBus.dispatch(new RenameWorkoutCommand(new WorkoutId(workoutId), request.getName()));
    return ResponseEntity.noContent().build();
  }

  @Override
  @Transactional
  public ResponseEntity<Void> reorderDays(UUID workoutId, ReorderDaysRequest request) {
    var dayIds = request.getDayIds().stream()
        .map(WorkoutDayId::new)
        .collect(Collectors.toList());
    commandBus.dispatch(new ReorderDaysCommand(new WorkoutId(workoutId), dayIds));
    return ResponseEntity.noContent().build();
  }
}
