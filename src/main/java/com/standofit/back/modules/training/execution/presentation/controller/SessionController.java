package com.standofit.back.modules.training.execution.presentation.controller;

import com.standofit.back.modules.training.execution.domain.SessionDomainException;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes;
import com.standofit.back.modules.training.execution.presentation.dto.AddExerciseLogRequest;
import com.standofit.back.modules.training.execution.presentation.dto.SessionDto;
import com.standofit.back.modules.training.execution.presentation.dto.StartSessionRequest;
import com.standofit.back.modules.training.execution.presentation.dto.UpdateNotesRequest;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionRepository repository;

    public SessionController(SessionRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String start(@RequestBody StartSessionRequest request) {
        Session session = Session.create(
                new SessionId(UUID.randomUUID()),
                new SessionDayId(request.dayId())
        );
        return repository.save(session).getId().value().toString();
    }

    @GetMapping("/{id}")
    public SessionDto getById(@PathVariable UUID id) {
        return repository.findById(new SessionId(id)) != null ? SessionDto.from(repository.findById(new SessionId(id))) : null;
    }

    @PostMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finish(@PathVariable UUID id) {
        Session session = repository.findById(new SessionId(id));
        repository.save(session.finish());
    }

    @PostMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable UUID id) {
        Session session = repository.findById(new SessionId(id));
        repository.save(session.cancel());
    }

    @PostMapping("/{id}/logs")
    @ResponseStatus(HttpStatus.CREATED)
    public void addLog(@PathVariable UUID id, @RequestBody AddExerciseLogRequest request) {
        Session session = repository.findById(new SessionId(id));
        var log = com.standofit.back.modules.training.execution.domain.entity.ExerciseLog.create(
                new com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId(UUID.fromString(request.logId())),
                new com.standofit.back.shared.domain.valueobjects.ids.ExerciseId(UUID.fromString(request.exerciseId())),
                new ExerciseLogSets(request.sets()),
                new ExerciseLogReps(request.reps()),
                new ExerciseLogWeight(request.weight()));
        repository.save(session.addLog(log));
    }

    @DeleteMapping("/{id}/logs/{logId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLog(@PathVariable UUID id, @PathVariable UUID logId) {
        Session session = repository.findById(new SessionId(id));
        repository.save(session.removeLog(new com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId(logId)));
    }

    @PutMapping("/{id}/notes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNotes(@PathVariable UUID id, @RequestBody UpdateNotesRequest request) {
        Session session = repository.findById(new SessionId(id));
        repository.save(session.changeNotes(new WorkoutSessionNotes(request.notes())));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        repository.delete(new SessionId(id));
    }

    @ExceptionHandler(SessionDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleDomainException(SessionDomainException e) {
    }
}
