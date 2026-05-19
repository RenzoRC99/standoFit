package com.standofit.back.modules.training.execution.domain.entity;

import com.standofit.back.modules.training.execution.domain.SessionDomainErrors;
import com.standofit.back.modules.training.execution.domain.SessionDomainException;
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
    return new Session(
        id,
        dayId,
        WorkoutSessionStatus.IN_PROGRESS,
        new ArrayList<>(),
        new WorkoutSessionNotes(""),
        new SessionCreatedAt(Instant.now()),
        new SessionUpdatedAt(Instant.now()));
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

  public Session update(
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
    return update(WorkoutSessionStatus.COMPLETED, logs, notes, new SessionUpdatedAt(Instant.now()));
  }

  public Session cancel() {
    if (status == WorkoutSessionStatus.COMPLETED) {
      throw new SessionDomainException(
          SessionDomainErrors.SESSION_CANNOT_BE_CANCELLED.getMessage());
    }
    if (status == WorkoutSessionStatus.CANCELLED) {
      throw new SessionDomainException(SessionDomainErrors.SESSION_ALREADY_CANCELLED.getMessage());
    }
    return update(WorkoutSessionStatus.CANCELLED, logs, notes, new SessionUpdatedAt(Instant.now()));
  }

  public Session changeNotes(WorkoutSessionNotes notes) {
    ensureInProgress();
    return update(status, this.logs, notes, new SessionUpdatedAt(Instant.now()));
  }

  public Session addLog(ExerciseLog log) {
    ensureInProgress();
    List<ExerciseLog> newLogs = new ArrayList<>(this.logs);
    newLogs.add(log);
    return update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
  }

  public Session removeLog(ExerciseLogId logId) {
    ensureInProgress();
    ensureLogExists(logId);
    List<ExerciseLog> newLogs =
        this.logs.stream().filter(log -> !log.getId().value().equals(logId.value())).toList();
    return update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
  }

  public Session updateLogSets(ExerciseLogId logId, ExerciseLogSets sets) {
    ensureInProgress();
    ensureLogExists(logId);
    List<ExerciseLog> newLogs =
        this.logs.stream()
            .map(log -> log.getId().value().equals(logId.value()) ? log.updateSets(sets) : log)
            .toList();
    return update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
  }

  public Session updateLogReps(ExerciseLogId logId, ExerciseLogReps reps) {
    ensureInProgress();
    ensureLogExists(logId);
    List<ExerciseLog> newLogs =
        this.logs.stream()
            .map(log -> log.getId().value().equals(logId.value()) ? log.updateReps(reps) : log)
            .toList();
    return update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
  }

  public Session updateLogWeight(ExerciseLogId logId, ExerciseLogWeight weight) {
    ensureInProgress();
    ensureLogExists(logId);
    List<ExerciseLog> newLogs =
        this.logs.stream()
            .map(log -> log.getId().value().equals(logId.value()) ? log.updateWeight(weight) : log)
            .toList();
    return update(status, newLogs, notes, new SessionUpdatedAt(Instant.now()));
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
