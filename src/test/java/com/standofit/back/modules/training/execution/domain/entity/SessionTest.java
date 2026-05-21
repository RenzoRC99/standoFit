package com.standofit.back.modules.training.execution.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.standofit.back.modules.training.execution.domain.SessionDomainException;
import com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Session Domain Tests")
class SessionTest {

  @Nested
  @DisplayName("Creation")
  class Creation {

    @Test
    @DisplayName("should create session in progress")
    void shouldCreateSessionInProgress() {
      Session session = SessionMother.aSession();

      assertNotNull(session.getId());
      assertNotNull(session.getDayId());
      assertEquals(WorkoutSessionStatus.IN_PROGRESS, session.getStatus());
      assertTrue(session.getLogs().isEmpty());
      assertNotNull(session.getCreatedAt());
      assertNotNull(session.getUpdatedAt());
    }

    @Test
    @DisplayName("should create session with specific day id")
    void shouldCreateSessionWithSpecificDayId() {
      var dayId = SessionMother.aSessionDayId();

      Session session = SessionMother.aSessionWithDayId(dayId);

      assertEquals(dayId, session.getDayId());
    }
  }

  @Nested
  @DisplayName("Finish Session")
  class FinishSession {

    @Test
    @DisplayName("should finish session")
    void shouldFinishSession() {
      Session session = SessionMother.aSessionInProgress();

      Session finished = session.finish();

      assertEquals(WorkoutSessionStatus.COMPLETED, finished.getStatus());
      assertNotNull(finished.getUpdatedAt());
    }

    @Test
    @DisplayName("should not finish already finished session")
    void shouldNotFinishAlreadyFinished() {
      Session session = SessionMother.aSessionCompleted();

      assertThrows(SessionDomainException.class, session::finish);
    }

    @Test
    @DisplayName("should not finish cancelled session")
    void shouldNotFinishCancelledSession() {
      Session session = SessionMother.aSessionCancelled();

      assertThrows(SessionDomainException.class, session::finish);
    }
  }

  @Nested
  @DisplayName("Cancel Session")
  class CancelSession {

    @Test
    @DisplayName("should cancel session")
    void shouldCancelSession() {
      Session session = SessionMother.aSessionInProgress();

      Session cancelled = session.cancel();

      assertEquals(WorkoutSessionStatus.CANCELLED, cancelled.getStatus());
    }

    @Test
    @DisplayName("should not cancel already finished session")
    void shouldNotCancelAlreadyFinished() {
      Session session = SessionMother.aSessionCompleted();

      assertThrows(SessionDomainException.class, session::cancel);
    }

    @Test
    @DisplayName("should not cancel already cancelled session")
    void shouldNotCancelAlreadyCancelled() {
      Session session = SessionMother.aSessionCancelled();

      assertThrows(SessionDomainException.class, session::cancel);
    }
  }

  @Nested
  @DisplayName("Manage Logs")
  class ManageLogs {

    @Test
    @DisplayName("should add log to session")
    void shouldAddLogToSession() {
      Session session = SessionMother.aSessionInProgress();
      var log = ExerciseLogMother.anExerciseLog();

      Session updated = session.addLog(log);

      assertEquals(1, updated.getLogs().size());
      assertEquals(log.getId(), updated.getLogs().get(0).getId());
    }

    @Test
    @DisplayName("should add multiple logs to session")
    void shouldAddMultipleLogsToSession() {
      Session session = SessionMother.aSessionInProgress();
      var log1 = ExerciseLogMother.anExerciseLog();
      var log2 = ExerciseLogMother.anExerciseLog();

      session = session.addLog(log1);
      Session updated = session.addLog(log2);

      assertEquals(2, updated.getLogs().size());
    }

    @Test
    @DisplayName("should not add log to finished session")
    void shouldNotAddLogToFinishedSession() {
      Session session = SessionMother.aSessionCompleted();
      var log = ExerciseLogMother.anExerciseLog();

      assertThrows(SessionDomainException.class, () -> session.addLog(log));
    }

    @Test
    @DisplayName("should remove log from session")
    void shouldRemoveLogFromSession() {
      var log = ExerciseLogMother.anExerciseLog();
      Session session = SessionMother.aSessionWithLogs(java.util.List.of(log));

      Session updated = session.removeLog(log.getId());

      assertTrue(updated.getLogs().isEmpty());
    }

    @Test
    @DisplayName("should fail when removing non-existent log")
    void shouldFailWhenRemovingNonExistentLog() {
      Session session = SessionMother.aSessionInProgress();
      var nonExistentLogId = ExerciseLogMother.anExerciseLogId();

      assertThrows(SessionDomainException.class, () -> session.removeLog(nonExistentLogId));
    }
  }

  @Nested
  @DisplayName("Update Logs")
  class UpdateLogs {

    @Test
    @DisplayName("should update log sets")
    void shouldUpdateLogSets() {
      var log = ExerciseLogMother.anExerciseLogWith(3, 10, 50);
      Session session = SessionMother.aSessionWithLogs(java.util.List.of(log));

      Session updated =
          session.updateLogSets(
              log.getId(),
              new com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets(5));

      assertEquals(5, updated.getLogs().get(0).getSets().value());
    }

    @Test
    @DisplayName("should update log reps")
    void shouldUpdateLogReps() {
      var log = ExerciseLogMother.anExerciseLogWith(3, 10, 50);
      Session session = SessionMother.aSessionWithLogs(java.util.List.of(log));

      Session updated =
          session.updateLogReps(
              log.getId(),
              new com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps(12));

      assertEquals(12, updated.getLogs().get(0).getReps().value());
    }

    @Test
    @DisplayName("should update log weight")
    void shouldUpdateLogWeight() {
      var log = ExerciseLogMother.anExerciseLogWith(3, 10, 50);
      Session session = SessionMother.aSessionWithLogs(java.util.List.of(log));

      Session updated =
          session.updateLogWeight(
              log.getId(),
              new com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight(60));

      assertEquals(60, updated.getLogs().get(0).getWeight().value());
    }
  }

  @Nested
  @DisplayName("Manage Notes")
  class ManageNotes {

    @Test
    @DisplayName("should change session notes")
    void shouldChangeSessionNotes() {
      Session session = SessionMother.aSessionInProgress();

      Session updated =
          session.changeNotes(
              new com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes(
                  "Updated notes"));

      assertEquals("Updated notes", updated.getNotes().value());
    }

    @Test
    @DisplayName("should not change notes on finished session")
    void shouldNotChangeNotesOnFinishedSession() {
      Session session = SessionMother.aSessionCompleted();

      assertThrows(
          SessionDomainException.class,
          () ->
              session.changeNotes(
                  new com.standofit.back.modules.training.execution.domain.vo.WorkoutSessionNotes(
                      "Notes")));
    }
  }
}
