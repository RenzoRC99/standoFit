package com.stedofit.back.modules.training.execution.presentation.controller;

import com.stedofit.back.api.execution.ApiApi;
import com.stedofit.back.api.execution.dto.AddExerciseLogRequest;
import com.stedofit.back.api.execution.dto.ErrorDTO;
import com.stedofit.back.api.execution.dto.ResponseDTO;
import com.stedofit.back.api.execution.dto.StartSessionRequest;
import com.stedofit.back.api.execution.dto.UpdateNotesRequest;
import com.stedofit.back.api.execution.dto.UpdateRepsRequest;
import com.stedofit.back.api.execution.dto.UpdateSetsRequest;
import com.stedofit.back.api.execution.dto.UpdateWeightRequest;
import com.stedofit.back.modules.training.execution.api.error.SessionNotFoundException;
import com.stedofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.stedofit.back.modules.training.execution.domain.entity.Session;
import com.stedofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.stedofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.stedofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.stedofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.stedofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.stedofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes;
import com.stedofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.stedofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.stedofit.back.shared.domain.valueobjects.ids.SessionId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
public class SessionController implements ApiApi {

    private final SessionRepository sessionRepository;

    public SessionController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public ResponseEntity<ResponseDTO> getAllSessions() {
        ResponseDTO response = new ResponseDTO();
        response.setPayload(java.util.List.of());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ResponseDTO> startSession(StartSessionRequest body) {
        Session session = Session.create(
                new SessionId(UUID.randomUUID()),
                new SessionDayId(body.getDayId())
        );
        Session saved = sessionRepository.save(session);
        ResponseDTO response = new ResponseDTO();
        response.setPayload(saved.getId().value().toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ResponseDTO> getSessionById(UUID sessionId) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            ResponseDTO response = new ResponseDTO();
            response.setError(new ErrorDTO("SESSION_NOT_FOUND", "Session not found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponseDTO response = new ResponseDTO();
        response.setPayload(session);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteSession(UUID sessionId) {
        sessionRepository.delete(new SessionId(sessionId));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> finishSession(UUID sessionId) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        sessionRepository.save(session.finish());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> cancelSession(UUID sessionId) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        sessionRepository.save(session.cancel());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ResponseDTO> getExerciseLogs(UUID sessionId) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            ResponseDTO response = new ResponseDTO();
            response.setError(new ErrorDTO("SESSION_NOT_FOUND", "Session not found"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ResponseDTO response = new ResponseDTO();
        response.setPayload(session.getLogs());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> addExerciseLog(UUID sessionId, AddExerciseLogRequest body) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        ExerciseLog log = ExerciseLog.create(
                new ExerciseLogId(UUID.randomUUID()),
                new ExerciseId(body.getExerciseId()),
                new ExerciseLogSets(body.getSets()),
                new ExerciseLogReps(body.getReps()),
                new ExerciseLogWeight(body.getWeight())
        );
        sessionRepository.save(session.addLog(log));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> removeExerciseLog(UUID sessionId, UUID logId) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        sessionRepository.save(session.removeLog(new ExerciseLogId(logId)));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateExerciseLogSets(UUID sessionId, UUID logId, UpdateSetsRequest body) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        sessionRepository.save(session.updateLogSets(new ExerciseLogId(logId), new ExerciseLogSets(body.getSets())));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateExerciseLogReps(UUID sessionId, UUID logId, UpdateRepsRequest body) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        sessionRepository.save(session.updateLogReps(new ExerciseLogId(logId), new ExerciseLogReps(body.getReps())));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateExerciseLogWeight(UUID sessionId, UUID logId, UpdateWeightRequest body) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        sessionRepository.save(session.updateLogWeight(new ExerciseLogId(logId), new ExerciseLogWeight(body.getWeight())));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateSessionNotes(UUID sessionId, UpdateNotesRequest body) {
        Session session = sessionRepository.findById(new SessionId(sessionId));
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        sessionRepository.save(session.changeNotes(new WorkoutSessionNotes(body.getNotes())));
        return ResponseEntity.noContent().build();
    }
}