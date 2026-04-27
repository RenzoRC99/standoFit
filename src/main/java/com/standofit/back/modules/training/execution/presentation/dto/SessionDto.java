package com.standofit.back.modules.training.execution.presentation.dto;

import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.entity.Session;

import java.time.Instant;
import java.util.List;

public record SessionDto(
        String id,
        String dayId,
        String status,
        List<ExerciseLogDto> logs,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
    public record ExerciseLogDto(
            String id,
            String exerciseId,
            int sets,
            int reps,
            int weight
    ) {
        public static ExerciseLogDto from(ExerciseLog log) {
            return new ExerciseLogDto(
                    log.getId().value().toString(),
                    log.getExerciseId().value().toString(),
                    log.getSets().value(),
                    log.getReps().value(),
                    log.getWeight().value()
            );
        }
    }

    public static SessionDto from(Session session) {
        return new SessionDto(
                session.getId().value().toString(),
                session.getDayId().value().toString(),
                session.getStatus().name(),
                session.getLogs().stream().map(ExerciseLogDto::from).toList(),
                session.getNotes().value(),
                session.getCreatedAt().value(),
                session.getUpdatedAt().value()
        );
    }
}