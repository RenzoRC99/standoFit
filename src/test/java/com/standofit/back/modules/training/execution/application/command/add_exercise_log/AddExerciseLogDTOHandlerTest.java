package com.standofit.back.modules.training.execution.application.command.add_exercise_log;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddExerciseLogDTOHandlerTest {

    @Mock
    private SessionRepository repository;

    private AddExerciseLogHandler handler;

    @BeforeEach
    void setUp() {
        handler = new AddExerciseLogHandler(repository);
    }

    @Test
    void should_add_exercise_log() {
        SessionId sessionId = new SessionId(UUID.randomUUID());
        SessionDayId dayId = new SessionDayId(UUID.randomUUID());
        ExerciseLogId logId = new ExerciseLogId(UUID.randomUUID());
        ExerciseId exerciseId = new ExerciseId(UUID.randomUUID());
        ExerciseLogSets sets = new ExerciseLogSets(3);
        ExerciseLogReps reps = new ExerciseLogReps(10);
        ExerciseLogWeight weight = new ExerciseLogWeight(50);
        AddExerciseLogCommand command = new AddExerciseLogCommand(
                sessionId,
                logId,
                exerciseId,
                sets,
                reps,
                weight
        );

        Session session = Session.create(sessionId, dayId);
        when(repository.findById(sessionId)).thenReturn(session);
        when(repository.save(any(Session.class))).thenReturn(session);

        handler.handle(command);

        verify(repository, times(1)).findById(sessionId);
        verify(repository, times(1)).save(any(Session.class));
    }

    @Test
    void should_throw_when_session_not_found() {
        SessionId sessionId = new SessionId(UUID.randomUUID());
        ExerciseLogId logId = new ExerciseLogId(UUID.randomUUID());
        ExerciseId exerciseId = new ExerciseId(UUID.randomUUID());
        ExerciseLogSets sets = new ExerciseLogSets(3);
        ExerciseLogReps reps = new ExerciseLogReps(10);
        ExerciseLogWeight weight = new ExerciseLogWeight(50);
        AddExerciseLogCommand command = new AddExerciseLogCommand(
                sessionId,
                logId,
                exerciseId,
                sets,
                reps,
                weight
        );

        when(repository.findById(sessionId)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> handler.handle(command));
    }
}
