package com.standofit.back.modules.training.planning.presentation;

import com.standofit.back.api.planning.PlanningApi;
import com.standofit.back.api.planning.dto.*;
import com.standofit.back.configuration.bus.ApplicationBus;
import com.standofit.back.modules.training.planning.application.command.archive_workout.ArchiveWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.change_workout_description.ChangeWorkoutDescriptionCommand;
import com.standofit.back.modules.training.planning.application.command.delete_workout.DeleteWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.duplicate_workout.DuplicateWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.remove_day_from_workout.RemoveDayFromWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.rename_workout.RenameWorkoutCommand;
import com.standofit.back.modules.training.planning.application.command.reorder_days.ReorderDaysCommand;
import com.standofit.back.modules.training.planning.application.query.get_workout_by_id.GetWorkoutByIdQuery;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanningApiController implements PlanningApi {

  private final ApplicationBus bus;

  public PlanningApiController(ApplicationBus bus) {
    this.bus = bus;
  }

  @Override
  public ResponseEntity<UUID> addDayToWorkout(UUID workoutId, AddDayRequest request) {
    bus.execute(PlanningCommandMapper.toCommand(workoutId, request));
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
    var result = bus.ask(PlanningQueryMapper.all());
    return ResponseEntity.ok(WorkoutApiMapper.toApi(result));
  }

  @Override
  public ResponseEntity<WorkoutPageDTO> searchWorkouts(SearchWorkoutsRequest request) {
    var result = bus.ask(PlanningQueryMapper.fromRequest(request));
    return ResponseEntity.ok(WorkoutApiMapper.toApi(result));
  }

  @Override
  public ResponseEntity<WorkoutDTO> getWorkoutById(UUID workoutId) {
    try {
      var dto = bus.ask(new GetWorkoutByIdQuery(new WorkoutId(workoutId)));
      return ResponseEntity.ok(WorkoutApiMapper.toApi(dto));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @Override
  public ResponseEntity<UUID> planWorkout(PlanWorkoutRequest request) {
    UUID workoutId = bus.execute(PlanningCommandMapper.toCommand(request));
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
}
