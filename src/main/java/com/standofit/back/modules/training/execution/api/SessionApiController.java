package com.standofit.back.modules.training.execution.api;

import com.standofit.back.api.execution.ApiApi;
import com.standofit.back.api.execution.dto.*;
import com.standofit.back.configuration.bus.ApplicationBus;
import com.standofit.back.modules.training.execution.api.error.SessionNotFoundException;
import com.standofit.back.modules.training.execution.application.command.cancel_session.CancelSessionCommand;
import com.standofit.back.modules.training.execution.application.command.delete_session.DeleteSessionCommand;
import com.standofit.back.modules.training.execution.application.command.finish_session.FinishSessionCommand;
import com.standofit.back.modules.training.execution.application.command.remove_exercise_log.RemoveExerciseLogCommand;
import com.standofit.back.modules.training.execution.application.command.start_session.StartSessionCommand;
import com.standofit.back.modules.training.execution.application.command.update_exercise_log_reps.UpdateExerciseLogRepsCommand;
import com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets.UpdateExerciseLogSetsCommand;
import com.standofit.back.modules.training.execution.application.command.update_exercise_log_weight.UpdateExerciseLogWeightCommand;
import com.standofit.back.modules.training.execution.application.command.update_session_notes.UpdateSessionNotesCommand;
import com.standofit.back.modules.training.execution.application.query.get_all_sessions.GetAllSessionsQuery;
import com.standofit.back.modules.training.execution.application.query.get_session_by_id.GetSessionByIdQuery;
import com.standofit.back.modules.training.execution.domain.vo.*;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
public class SessionApiController implements ApiApi {

  private final ApplicationBus bus;

  public SessionApiController(ApplicationBus bus) {
        this.bus = bus;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ApiApi.super.getRequest();
    }

    @Override
    public ResponseEntity<SessionListDTO> getAllSessions() {
        SessionListDTO payload = bus.ask(new GetAllSessionsQuery());
        if (payload.getSessions().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(payload);
    }

    @Override
    public ResponseEntity<Void> startSession(StartSessionRequest body) {
        UUID sessionId = bus.execute(new StartSessionCommand(new SessionDayId(body.getDayId())));
        URI location =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(sessionId)
                        .toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<SessionDTO> getSessionById(UUID sessionId) {
        try {
            SessionDTO payload = bus.ask(new GetSessionByIdQuery(new SessionId(sessionId)));
            return ResponseEntity.ok(payload);
        } catch (IllegalArgumentException e) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    @Override
    public ResponseEntity<Void> deleteSession(UUID sessionId) {
        bus.execute(new DeleteSessionCommand(new SessionId(sessionId)));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> finishSession(UUID sessionId) {
        try {
            bus.execute(new FinishSessionCommand(new SessionId(sessionId)));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    @Override
    public ResponseEntity<Void> cancelSession(UUID sessionId) {
        try {
            bus.execute(new CancelSessionCommand(new SessionId(sessionId)));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    @Override
    public ResponseEntity<Void> removeExerciseLog(UUID sessionId, UUID logId) {
        try {
            bus.execute(new RemoveExerciseLogCommand(new SessionId(sessionId), new ExerciseLogId(logId)));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    @Override
    public ResponseEntity<Void> updateExerciseLogSets(
            UUID sessionId, UUID logId, UpdateSetsRequest body) {
        try {
            bus.execute(
                    new UpdateExerciseLogSetsCommand(
                            new SessionId(sessionId),
                            new ExerciseLogId(logId),
                            new ExerciseLogSets(body.getSets())));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    @Override
    public ResponseEntity<Void> updateExerciseLogReps(
            UUID sessionId, UUID logId, UpdateRepsRequest body) {
        try {
            bus.execute(
                    new UpdateExerciseLogRepsCommand(
                            new SessionId(sessionId),
                            new ExerciseLogId(logId),
                            new ExerciseLogReps(body.getReps())));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    @Override
    public ResponseEntity<Void> updateExerciseLogWeight(
            UUID sessionId, UUID logId, UpdateWeightRequest body) {
        try {
            bus.execute(
                    new UpdateExerciseLogWeightCommand(
                            new SessionId(sessionId),
                            new ExerciseLogId(logId),
                            new ExerciseLogWeight(body.getWeight())));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new SessionNotFoundException(sessionId);
        }
    }

    @Override
    public ResponseEntity<Void> updateSessionNotes(UUID sessionId, UpdateNotesRequest body) {
        try {
            bus.execute(
                    new UpdateSessionNotesCommand(
                            new SessionId(sessionId), new WorkoutSessionNotes(body.getNotes())));
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new SessionNotFoundException(sessionId);
        }
    }
}
