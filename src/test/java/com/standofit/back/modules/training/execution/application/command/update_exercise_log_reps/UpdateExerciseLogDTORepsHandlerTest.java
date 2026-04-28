package com.standofit.back.modules.training.execution.application.command.update_exercise_log_reps;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
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
class UpdateExerciseLogDTORepsHandlerTest {

    @Mock
    private SessionRepository repository;

    private UpdateExerciseLogRepsHandler handler;

    @BeforeEach
    void setUp() {
        handler = new UpdateExerciseLogRepsHandler(repository);
    }

    @Test
    void should_call_repository_to_update_reps() {
        SessionId sessionId = new SessionId(UUID.randomUUID());
        SessionDayId dayId = new SessionDayId(UUID.randomUUID());
        ExerciseLogId logId = new ExerciseLogId(UUID.randomUUID());
        ExerciseLogReps reps = new ExerciseLogReps(12);
        UpdateExerciseLogRepsCommand command = new UpdateExerciseLogRepsCommand(sessionId, logId, reps);

        Session session = Session.create(sessionId, dayId);
        when(repository.findById(sessionId)).thenReturn(session);
        when(repository.save(any(Session.class))).thenReturn(session);

        handler.handle(command);

        verify(repository, times(1)).findById(sessionId);
        verify(repository, times(1)).save(any(Session.class));
    }
}
