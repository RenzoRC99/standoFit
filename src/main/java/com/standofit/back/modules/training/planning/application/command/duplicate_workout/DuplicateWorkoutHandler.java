package com.standofit.back.modules.training.planning.application.command.duplicate_workout;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.*;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DuplicateWorkoutHandler implements CommandHandler<DuplicateWorkoutCommand, UUID> {

    private final WorkoutRepository repository;

    public DuplicateWorkoutHandler(WorkoutRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<DuplicateWorkoutCommand> commandType() {
        return DuplicateWorkoutCommand.class;
    }

    @Override
    public UUID handle(DuplicateWorkoutCommand command) {
        Workout original = repository.findById(command.workoutId())
                .orElseThrow(() -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

        List<WorkoutDay> duplicatedDays = new ArrayList<>();
        for (WorkoutDay day : original.getDays()) {
            List<WorkoutExercise> duplicatedExercises = new ArrayList<>();
            for (WorkoutExercise ex : day.getExercises()) {
                WorkoutExercise newEx = WorkoutExercise.create(
                        new WorkoutExerciseId(UUID.randomUUID()),
                        new ExerciseId(ex.getExerciseId().value()),
                        new WorkoutExerciseSets(ex.getSets().value()),
                        new WorkoutExerciseReps(ex.getReps().value()),
                        new WorkoutExerciseRest(ex.getRestSeconds().value())
                );
                duplicatedExercises.add(newEx);
            }

            WorkoutDay newDay = WorkoutDay.create(
                    new WorkoutDayId(UUID.randomUUID()),
                    new WorkoutDayName(day.getName().value()),
                    duplicatedExercises
            );
            duplicatedDays.add(newDay);
        }

        WorkoutName newName = command.newName() != null && !command.newName().isBlank()
                ? new WorkoutName(command.newName())
                : new WorkoutName(original.getName().value() + " (Copy)");

        Workout duplicated = Workout.create(
                new WorkoutId(UUID.randomUUID()),
                original.getDescription() != null
                        ? new WorkoutDescription(original.getDescription().value())
                        : new WorkoutDescription(""),
                newName,
                duplicatedDays
        );

        // TODO: Publish WorkoutDuplicatedEvent
        // eventBus.publish(new WorkoutDuplicatedEvent(original.getId(), duplicated.getId()));

        return repository.save(duplicated).getId().value();
    }
}
