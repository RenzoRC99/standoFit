package com.standofit.back.modules.training.execution.presentation;

import com.standofit.back.api.execution.ExecutionApi;
import com.standofit.back.api.execution.dto.*;
import com.standofit.back.configuration.bus.ApplicationBus;
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
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ExecutionApiController implements ExecutionApi {

    private final ApplicationBus bus;

    public ExecutionApiController(ApplicationBus bus) {
        this.bus = bus;
    }

    @Override
    public ResponseEntity<SessionListDTO> getAllSessions() {
        SessionListDto result = bus.ask(new GetAllSessionsQuery());
        if (result.sessions().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(toApiSessionListDTO(result));
    }

    @Override
    public ResponseEntity<SessionDTO> getSessionById(UUID sessionId) {
        SessionDto result = bus.ask(new GetSessionByIdQuery(new SessionId(sessionId)));
        return ResponseEntity.ok(toApiSessionDTO(result));
    }

    @Override
    public ResponseEntity<Void> startSession(StartSessionRequest request) {
        bus.execute(new StartSessionCommand(new SessionDayId(request.getDayId())));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteSession(UUID sessionId) {
        bus.execute(new DeleteSessionCommand(new SessionId(sessionId)));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> finishSession(UUID sessionId) {
        bus.execute(new FinishSessionCommand(new SessionId(sessionId)));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> cancelSession(UUID sessionId) {
        bus.execute(new CancelSessionCommand(new SessionId(sessionId)));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> addExerciseLog(UUID sessionId, AddExerciseLogRequest request) {
        bus.execute(
                new AddExerciseLogCommand(
                        new SessionId(sessionId),
                        new ExerciseLogId(UUID.randomUUID()),
                        new ExerciseId(request.getExerciseId()),
                        new ExerciseLogSets(request.getSets()),
                        new ExerciseLogReps(request.getReps()),
                        new ExerciseLogWeight(request.getWeight())
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> removeExerciseLog(UUID sessionId, UUID logId) {
        bus.execute(new RemoveExerciseLogCommand(new SessionId(sessionId), new ExerciseLogId(logId)));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateExerciseLogSets(UUID sessionId, UUID logId, UpdateSetsRequest request) {
        bus.execute(
                new UpdateExerciseLogSetsCommand(
                        new SessionId(sessionId),
                        new ExerciseLogId(logId),
                        new ExerciseLogSets(request.getSets())
                )
        );
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateExerciseLogReps(UUID sessionId, UUID logId, UpdateRepsRequest request) {
        bus.execute(
                new UpdateExerciseLogRepsCommand(
                        new SessionId(sessionId),
                        new ExerciseLogId(logId),
                        new ExerciseLogReps(request.getReps())
                )
        );
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateExerciseLogWeight(UUID sessionId, UUID logId, UpdateWeightRequest request) {
        bus.execute(
                new UpdateExerciseLogWeightCommand(
                        new SessionId(sessionId),
                        new ExerciseLogId(logId),
                        new ExerciseLogWeight(request.getWeight())
                )
        );
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateSessionNotes(UUID sessionId, UpdateNotesRequest request) {
        bus.execute(new UpdateSessionNotesCommand(new SessionId(sessionId), new WorkoutSessionNotes(request.getNotes())));
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
