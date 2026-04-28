package com.standofit.back.modules.training.execution.application.command.update_exercise_log_weight;

import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.vo.*;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateExerciseLogDTOWeightHandlerTest {

    @Mock
    private SessionRepository repository;

    private UpdateExerciseLogWeightHandler handler;

    @BeforeEach
    void setUp() {
        handler = new UpdateExerciseLogWeightHandler(repository);
    }

    @Test
    void should_call_repository_to_update_weight() {
        SessionId sessionId = new SessionId(UUID.randomUUID());
        SessionDayId dayId = new SessionDayId(UUID.randomUUID());
        ExerciseLogId logId = new ExerciseLogId(UUID.randomUUID());
        ExerciseLogWeight weight = new ExerciseLogWeight(60);
        UpdateExerciseLogWeightCommand command =
                new UpdateExerciseLogWeightCommand(sessionId, logId, weight);

    Session session = Session.create(sessionId, dayId);
    ExerciseLog log = ExerciseLog.create(logId, new ExerciseId(UUID.randomUUID()), new ExerciseLogSets(3), new ExerciseLogReps(10), new ExerciseLogWeight(50));
    session = session.addLog(log);
    when(repository.findById(sessionId)).thenReturn(session);
        when(repository.save(any(Session.class))).thenReturn(session);

        handler.handle(command);

        verify(repository, times(1)).findById(sessionId);
        verify(repository, times(1)).save(any(Session.class));
    }
}
