package com.standofit.back.modules.training.execution.domain.entity;

import com.standofit.back.modules.training.execution.domain.SessionDomainErrors;
import com.standofit.back.modules.training.execution.domain.SessionDomainException;
import com.standofit.back.modules.training.execution.domain.event.ExerciseLogAdded;
import com.standofit.back.modules.training.execution.domain.event.ExerciseLogRemoved;
import com.standofit.back.modules.training.execution.domain.event.ExerciseLogRepsUpdated;
import com.standofit.back.modules.training.execution.domain.event.ExerciseLogSetsUpdated;
import com.standofit.back.modules.training.execution.domain.event.ExerciseLogWeightUpdated;
import com.standofit.back.modules.training.execution.domain.event.SessionCancelled;
import com.standofit.back.modules.training.execution.domain.event.SessionDeleted;
import com.standofit.back.modules.training.execution.domain.event.SessionFinished;
import com.standofit.back.modules.training.execution.domain.event.SessionNotesChanged;
import com.standofit.back.modules.training.execution.domain.event.SessionStarted;
import com.standofit.back.modules.training.execution.domain.vo.*;
import com.standofit.back.shared.domain.aggregate.AggregateRoot;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class Session extends AggregateRoot {

  private final SessionId id;
  private final SessionDayId dayId;
  private final WorkoutSessionStatus status;
  private final List<ExerciseLog> logs;
  private final WorkoutSessionNotes notes;
  private final SessionCreatedAt createdAt;
  private final SessionUpdatedAt updatedAt;

  private Session(
      SessionId id,
      SessionDayId dayId,
      WorkoutSessionStatus status,
      List<ExerciseLog> logs,
      WorkoutSessionNotes notes,
      SessionCreatedAt createdAt,
      SessionUpdatedAt updatedAt) {
    this.id = id;
    this.dayId = dayId;
    this.status = status;
    this.logs = logs != null ? List.copyOf(logs) : List.of();
    this.notes = notes;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static Session create(SessionId id, SessionDayId dayId) {
    Session session =
        new Session(
            id,
            dayId,
            WorkoutSessionStatus.IN_PROGRESS,
            new ArrayList<>(),
            new WorkoutSessionNotes(""),
            new SessionCreatedAt(Instant.now()),
            new SessionUpdatedAt(Instant.now()));
    session.record(new SessionStarted(id, dayId));
    return session;
  }

  public static Session copy(
      SessionId id,
      SessionDayId dayId,
      WorkoutSessionStatus status,
      List<ExerciseLog> logs,
      WorkoutSessionNotes notes,
      SessionCreatedAt createdAt,
      SessionUpdatedAt updatedAt) {
    return new Session(id, dayId, status, logs, notes, createdAt, updatedAt);
  }

  private Session update(
      WorkoutSessionStatus status,
      List<ExerciseLog> logs,
      WorkoutSessionNotes notes,
      SessionUpdatedAt updatedAt) {
    return new Session(id, dayId, status, logs, notes, this.createdAt, updatedAt);
  }

  public Session finish() {
    if (status == WorkoutSessionStatus.COMPLETED) {
      throw new SessionDomainException(SessionDomainErrors.SESSION_ALREADY_FINISHED.getMessage());
    }
    if (status == WorkoutSessionStatus.CANCELLED) {
      throw new SessionDomainException(SessionDomainErrors.SESSION_CANNOT_BE_FINISHED.getMessage());
    }
    Session updated =
        update(WorkoutSessionStatus.COMPLETED, logs, notes, new SessionUpdatedAt(Instant.now()));
    updated.record(new SessionFinished(id));
    return updated;
  }

  public Session cancel() {
    if (status == WorkoutSessionStatus.COMPLETED) {
      throw new SessionDomainException(
          SessionDomainErrors.SESSION_CANNOT_BE_CANCELLED.getMessage());
    }
    if (status == WorkoutSessionStatus.CANCELLED) {
      throw new SessionDomainException(SessionDomainErrors.SESSION_ALREADY_CANCELLED.getMessage());
    }
    Session updated =
        update(WorkoutSessionStatus.CANCELLED, logs, notes, new SessionUpdatedAt(Instant.now()));
    updated.record(new SessionCancelled(id));
    return updated;
  }

  public Session delete() {
    if (status != WorkoutSessionStatus.COMPLETED && status != WorkoutSessionStatus.CANCELLED) {
      throw new SessionDomainException(SessionDomainErrors.SESSION_CANNOT_BE_DELETED.getMessage());
    }
    Session updated = update(status, logs, notes, new SessionUpdatedAt(Instant.now()));
    updated.record(new SessionDeleted(id));
    return updated;
  }

  public Session changeNotes(WorkoutSessionNotes notes) {
    ensureInProgress();
    Session updated = update(status, this.logs, notes, new SessionUpdatedAt(Instant.now()));
    updated.record(new SessionNotesChanged(id, notes));
    return updated;
  }

  public Session addLog(ExerciseLog log) {
    ensureInProgress();
    List<ExerciseLog> newLogs = new ArrayList<>(this.logs);
    newLogs.add(log);
    Session updated = update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
    updated.record(new ExerciseLogAdded(id, log));
    return updated;
  }

  public Session removeLog(ExerciseLogId logId) {
    ensureInProgress();
    ensureLogExists(logId);
    List<ExerciseLog> newLogs =
        this.logs.stream().filter(log -> !log.getId().value().equals(logId.value())).toList();
    Session updated = update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
    updated.record(new ExerciseLogRemoved(id, logId));
    return updated;
  }

  public Session updateLogSets(ExerciseLogId logId, ExerciseLogSets sets) {
    ensureInProgress();
    ensureLogExists(logId);
    List<ExerciseLog> newLogs =
        this.logs.stream()
            .map(log -> log.getId().value().equals(logId.value()) ? log.updateSets(sets) : log)
            .toList();
    Session updated = update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
    updated.record(new ExerciseLogSetsUpdated(id, logId, sets));
    return updated;
  }

  public Session updateLogReps(ExerciseLogId logId, ExerciseLogReps reps) {
    ensureInProgress();
    ensureLogExists(logId);
    List<ExerciseLog> newLogs =
        this.logs.stream()
            .map(log -> log.getId().value().equals(logId.value()) ? log.updateReps(reps) : log)
            .toList();
    Session updated = update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
    updated.record(new ExerciseLogRepsUpdated(id, logId, reps));
    return updated;
  }

  public Session updateLogWeight(ExerciseLogId logId, ExerciseLogWeight weight) {
    ensureInProgress();
    ensureLogExists(logId);
    List<ExerciseLog> newLogs =
        this.logs.stream()
            .map(log -> log.getId().value().equals(logId.value()) ? log.updateWeight(weight) : log)
            .toList();
    Session updated = update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
    updated.record(new ExerciseLogWeightUpdated(id, logId, weight));
    return updated;
  }

  private void ensureInProgress() {
    if (status != WorkoutSessionStatus.IN_PROGRESS) {
      throw new SessionDomainException(SessionDomainErrors.SESSION_CANNOT_BE_MODIFIED.getMessage());
    }
  }

  private void ensureLogExists(ExerciseLogId logId) {
    boolean exists = logs.stream().anyMatch(log -> log.getId().value().equals(logId.value()));
    if (!exists) {
      throw new SessionDomainException(SessionDomainErrors.LOG_NOT_FOUND.getMessage());
    }
  }

  public SessionId getId() {
    return id;
  }

  public SessionDayId getDayId() {
    return dayId;
  }

  public WorkoutSessionStatus getStatus() {
    return status;
  }

  public List<ExerciseLog> getLogs() {
    return logs;
  }

  public WorkoutSessionNotes getNotes() {
    return notes;
  }

  public SessionCreatedAt getCreatedAt() {
    return createdAt;
  }

  public SessionUpdatedAt getUpdatedAt() {
    return updatedAt;
  }
}
