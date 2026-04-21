package com.standofit.back.training.planning.infrastructure;

import com.standofit.back.training.planning.domain.entity.Workout;
import com.standofit.back.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.training.planning.domain.vo.WorkoutDescription;
import com.standofit.back.training.planning.domain.vo.WorkoutName;
import com.standofit.back.training.planning.domain.vo.WorkoutDayName;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseSets;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final WorkoutRepository repository;

    public DataInitializer(WorkoutRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")).isEmpty()) {
            Workout workout = Workout.create(
                new WorkoutId(UUID.fromString("00000000-0000-0000-0000-000000000001")),
                new WorkoutDescription("Full body workout for beginners"),
                new WorkoutName("Full Body Workout"),
                List.of(
                    com.standofit.back.training.planning.domain.entity.WorkoutDay.create(
                        new WorkoutDayId(UUID.fromString("00000000-0000-0000-0000-000000000011")),
                        new WorkoutDayName("Day 1 - Chest & Triceps"),
                        List.of(
                            com.standofit.back.training.planning.domain.entity.WorkoutExercise.create(
                                new WorkoutExerciseId(UUID.fromString("00000000-0000-0000-0000-000000000021")),
                                new ExerciseId(UUID.fromString("00000000-0000-0000-0000-000000000001")),
                                new WorkoutExerciseSets(4),
                                new WorkoutExerciseReps(10),
                                new WorkoutExerciseRest(60)
                            ),
                            com.standofit.back.training.planning.domain.entity.WorkoutExercise.create(
                                new WorkoutExerciseId(UUID.fromString("00000000-0000-0000-0000-000000000022")),
                                new ExerciseId(UUID.fromString("00000000-0000-0000-0000-000000000002")),
                                new WorkoutExerciseSets(3),
                                new WorkoutExerciseReps(12),
                                new WorkoutExerciseRest(45)
                            )
                        )
                    ),
                    com.standofit.back.training.planning.domain.entity.WorkoutDay.create(
                        new WorkoutDayId(UUID.fromString("00000000-0000-0000-0000-000000000012")),
                        new WorkoutDayName("Day 2 - Back & Biceps"),
                        List.of(
                            com.standofit.back.training.planning.domain.entity.WorkoutExercise.create(
                                new WorkoutExerciseId(UUID.fromString("00000000-0000-0000-0000-000000000023")),
                                new ExerciseId(UUID.fromString("00000000-0000-0000-0000-000000000003")),
                                new WorkoutExerciseSets(4),
                                new WorkoutExerciseReps(8),
                                new WorkoutExerciseRest(60)
                            )
                        )
                    )
                )
            );
            repository.save(workout);
        }
    }
}