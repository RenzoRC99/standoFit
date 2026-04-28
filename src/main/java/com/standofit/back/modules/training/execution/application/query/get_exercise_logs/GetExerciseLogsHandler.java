package com.standofit.back.modules.training.execution.application.query.get_exercise_logs;

import com.standofit.back.api.execution.dto.ExerciseLogDTO;
import com.standofit.back.modules.training.execution.domain.entity.Session;
import com.standofit.back.modules.training.execution.domain.entity.SessionRepository;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetExerciseLogsHandler implements QueryHandler<GetExerciseLogsQuery, List<ExerciseLogDTO>> {

    private final SessionRepository repository;

    public GetExerciseLogsHandler(SessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<GetExerciseLogsQuery> queryType() {
        return GetExerciseLogsQuery.class;
    }

    @Override
    public List<ExerciseLogDTO> handle(GetExerciseLogsQuery query) {
        Session session = repository.findById(query.sessionId());
        if (session == null) {
            throw new IllegalArgumentException("Session not found: " + query.sessionId());
        }
        return session.getLogs().stream()
                .map(log -> new ExerciseLogDTO()
                        .id(log.getId().value())
                        .exerciseId(log.getExerciseId().value())
                        .sets(log.getSets().value())
                        .reps(log.getReps().value())
                        .weight(log.getWeight().value())
                )
                .toList();
    }
}
