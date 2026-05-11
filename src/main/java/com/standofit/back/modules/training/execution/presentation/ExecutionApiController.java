package com.standofit.back.modules.training.execution.presentation;

import com.standofit.back.api.execution.ExecutionApi;
import com.standofit.back.api.execution.dto.*;
import com.standofit.back.modules.training.execution.application.command.add_exercise_log.AddExerciseLogCommand;
import com.standofit.back.modules.training.execution.application.command.cancel_session.CancelSessionCommand;
import com.standofit.back.modules.training.execution.application.command.delete_session.DeleteSessionCommand;
import com.standofit.back.modules.training.execution.application.command.finish_session.FinishSessionCommand;
import com.standofit.back.modules.training.execution.application.command.remove_exercise_log.RemoveExerciseLogCommand;
import com.standofit.back.modules.training.execution.application.command.start_session.StartSessionCommand;
import com.standofit.back.modules.training.execution.application.command.update_exercise_log_reps.UpdateExerciseLogRepsCommand;
import com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets.UpdateExerciseLogSetsCommand;
import com.standofit.back.modules.training.execution.application.command.update_exercise_log_weight.UpdateExerciseLogWeightCommand;
import com.standofit.back.modules.training.execution.application.command.update_session_notes.UpdateSessionNotesCommand;
import com.standofit.back.modules.training.execution.application.dto.SessionDto;
import com.standofit.back.modules.training.execution.application.dto.SessionListDto;
import com.standofit.back.modules.training.execution.application.query.get_all_sessions.GetAllSessionsQuery;
import com.standofit.back.modules.training.execution.application.query.get_session_by_id.GetSessionByIdQuery;
import com.standofit.back.modules.training.execution.domain.vo.*;
import com.standofit.back.shared.domain.bus.command.CommandBus;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionApiController implements ExecutionApi {

  private final CommandBus commandBus;
  private final QueryBus queryBus;

  public ExecutionApiController(CommandBus commandBus, QueryBus queryBus) {
    this.commandBus = commandBus;
    this.queryBus = queryBus;
  }

  @Override
  public ResponseEntity<SessionListDTO> getAllSessions() {
    SessionListDto result = queryBus.ask(new GetAllSessionsQuery());
    if (result.sessions().isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(toApiSessionListDTO(result));
  }

  @Override
  public ResponseEntity<SessionDTO> getSessionById(UUID sessionId) {
    SessionDto result = queryBus.ask(new GetSessionByIdQuery(new SessionId(sessionId)));
    return ResponseEntity.ok(toApiSessionDTO(result));
  }

  @Override
  public ResponseEntity<UUID> startSession(StartSessionRequest request) {
    UUID sessionId =
        commandBus.dispatch(new StartSessionCommand(new SessionDayId(request.getDayId())));
    return ResponseEntity.status(HttpStatus.CREATED).body(sessionId);
  }

  @Override
  public ResponseEntity<Void> deleteSession(UUID sessionId) {
    commandBus.dispatch(new DeleteSessionCommand(new SessionId(sessionId)));
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> finishSession(UUID sessionId) {
    commandBus.dispatch(new FinishSessionCommand(new SessionId(sessionId)));
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> cancelSession(UUID sessionId) {
    commandBus.dispatch(new CancelSessionCommand(new SessionId(sessionId)));
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> addExerciseLog(UUID sessionId, AddExerciseLogRequest request) {
    commandBus.dispatch(
        new AddExerciseLogCommand(
            new SessionId(sessionId),
            new ExerciseLogId(UUID.randomUUID()),
            new ExerciseId(request.getExerciseId()),
            new ExerciseLogSets(request.getSets()),
            new ExerciseLogReps(request.getReps()),
            new ExerciseLogWeight(request.getWeight())));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Override
  public ResponseEntity<Void> removeExerciseLog(UUID sessionId, UUID logId) {
    commandBus.dispatch(
        new RemoveExerciseLogCommand(new SessionId(sessionId), new ExerciseLogId(logId)));
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> updateExerciseLogSets(
      UUID sessionId, UUID logId, UpdateSetsRequest request) {
    commandBus.dispatch(
        new UpdateExerciseLogSetsCommand(
            new SessionId(sessionId),
            new ExerciseLogId(logId),
            new ExerciseLogSets(request.getSets())));
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> updateExerciseLogReps(
      UUID sessionId, UUID logId, UpdateRepsRequest request) {
    commandBus.dispatch(
        new UpdateExerciseLogRepsCommand(
            new SessionId(sessionId),
            new ExerciseLogId(logId),
            new ExerciseLogReps(request.getReps())));
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> updateExerciseLogWeight(
      UUID sessionId, UUID logId, UpdateWeightRequest request) {
    commandBus.dispatch(
        new UpdateExerciseLogWeightCommand(
            new SessionId(sessionId),
            new ExerciseLogId(logId),
            new ExerciseLogWeight(request.getWeight())));
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> updateSessionNotes(UUID sessionId, UpdateNotesRequest request) {
    commandBus.dispatch(
        new UpdateSessionNotesCommand(
            new SessionId(sessionId), new WorkoutSessionNotes(request.getNotes())));
    return ResponseEntity.noContent().build();
  }

  private SessionDTO toApiSessionDTO(SessionDto dto) {
    return new SessionDTO()
        .id(dto.id())
        .dayId(dto.dayId())
        .status(SessionDTO.StatusEnum.valueOf(dto.status()))
        .notes(dto.notes())
        .startedAt(dto.startedAt())
        .finishedAt(dto.finishedAt());
  }

  private SessionListDTO toApiSessionListDTO(SessionListDto dto) {
    return new SessionListDTO()
        .sessions(dto.sessions().stream().map(this::toApiSessionDTO).toList());
  }
}
