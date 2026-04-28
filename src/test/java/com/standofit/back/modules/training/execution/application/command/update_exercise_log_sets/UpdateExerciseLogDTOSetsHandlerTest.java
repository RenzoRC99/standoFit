package com.standofit.back.modules.training.execution.application.command.update_exercise_log_sets;

import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateExerciseLogDTOSetsHandlerTest {

    @Mock
    private SessionRepository repository;

    private UpdateExerciseLogSetsHandler handler;

    @BeforeEach
    void setUp() {
        handler = new UpdateExerciseLogSetsHandler(repository);
    }

    @Test
    void should_call_repository_to_update_sets_when_log_exists() {
        SessionId sessionId = new SessionId(UUID.randomUUID());
        SessionDayId dayId = new SessionDayId(UUID.randomUUID());
        ExerciseLogId logId = new ExerciseLogId(UUID.randomUUID());
        ExerciseLogSets sets = new ExerciseLogSets(4);
        UpdateExerciseLogSetsCommand command = new UpdateExerciseLogSetsCommand(sessionId, logId, sets);

        Session session = Session.create(sessionId, dayId);
        when(repository.findById(sessionId)).thenReturn(session);

        handler.handle(command);
    }
}
