package com.standofit.back.modules.training.planning.application.command.plan_workout;

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
public class PlanWorkoutHandler implements CommandHandler<PlanWorkoutCommand, UUID> {

    private final WorkoutRepository repository;

    public PlanWorkoutHandler(WorkoutRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<PlanWorkoutCommand> commandType() {
        return PlanWorkoutCommand.class;
    }

    @Override
    public UUID handle(PlanWorkoutCommand command) {
        List<WorkoutDay> days = new ArrayList<>();

        for (PlanWorkoutCommand.DayInput dayInput : command.days()) {
            List<WorkoutExercise> exercises = new ArrayList<>();

            for (PlanWorkoutCommand.ExerciseInput exInput : dayInput.exercises()) {
                WorkoutExercise exercise = WorkoutExercise.create(
                        new WorkoutExerciseId(UUID.randomUUID()),
                        new ExerciseId(exInput.exerciseId()),
                        new WorkoutExerciseSets(exInput.sets()),
                        new WorkoutExerciseReps(exInput.reps()),
                        new WorkoutExerciseRest(exInput.restSeconds())
                );
                exercises.add(exercise);
            }

            WorkoutDay day = WorkoutDay.create(
                    new WorkoutDayId(UUID.randomUUID()),
                    new WorkoutDayName(dayInput.name()),
                    exercises
            );
            days.add(day);
        }

        Workout workout = Workout.create(
                new WorkoutId(UUID.randomUUID()),
                new WorkoutDescription(command.description()),
                new WorkoutName(command.name()),
                days
        );

        // TODO: Publish WorkoutPlannedEvent
        // eventBus.publish(new WorkoutPlannedEvent(workout.getId(), ...));

        return repository.save(workout).getId().value();
    }
}
