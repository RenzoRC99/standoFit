package com.standofit.back.modules.training.execution.domain.entity;

import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogId;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogReps;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogSets;
import com.standofit.back.modules.training.execution.domain.vo.ExerciseLogWeight;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExerciseLogDTOTest {

    @Test
    void should_create_exercise_log() {
        ExerciseLogId id = new ExerciseLogId(UUID.randomUUID());
        ExerciseId exerciseId = new ExerciseId(UUID.randomUUID());
        ExerciseLogSets sets = new ExerciseLogSets(3);
        ExerciseLogReps reps = new ExerciseLogReps(10);
        ExerciseLogWeight weight = new ExerciseLogWeight(50);

        ExerciseLog log = ExerciseLog.create(id, exerciseId, sets, reps, weight);

        assertEquals(id, log.getId());
        assertEquals(exerciseId, log.getExerciseId());
        assertEquals(3, log.getSets().value());
        assertEquals(10, log.getReps().value());
        assertEquals(50, log.getWeight().value());
    }

    @Test
    void should_update_sets() {
        ExerciseLog log = ExerciseLog.create(
                new ExerciseLogId(UUID.randomUUID()),
                new ExerciseId(UUID.randomUUID()),
                new ExerciseLogSets(3),
                new ExerciseLogReps(10),
                new ExerciseLogWeight(50)
        );

        ExerciseLog updated = log.updateSets(new ExerciseLogSets(4));

        assertEquals(4, updated.getSets().value());
    }

    @Test
    void should_update_reps() {
        ExerciseLog log = ExerciseLog.create(
                new ExerciseLogId(UUID.randomUUID()),
                new ExerciseId(UUID.randomUUID()),
                new ExerciseLogSets(3),
                new ExerciseLogReps(10),
                new ExerciseLogWeight(50)
        );

        ExerciseLog updated = log.updateReps(new ExerciseLogReps(12));

        assertEquals(12, updated.getReps().value());
    }

    @Test
    void should_update_weight() {
        ExerciseLog log = ExerciseLog.create(
                new ExerciseLogId(UUID.randomUUID()),
                new ExerciseId(UUID.randomUUID()),
                new ExerciseLogSets(3),
                new ExerciseLogReps(10),
                new ExerciseLogWeight(50)
        );

        ExerciseLog updated = log.updateWeight(new ExerciseLogWeight(60));

        assertEquals(60, updated.getWeight().value());
    }
}
