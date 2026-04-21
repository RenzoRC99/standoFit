package com.standofit.back.training.planning.application.command.create_workout;

import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutId;
import com.standofit.back.training.planning.domain.entity.Workout;
import com.standofit.back.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.training.planning.domain.vo.WorkoutDescription;
import com.standofit.back.training.planning.domain.vo.WorkoutDayName;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.training.planning.domain.vo.WorkoutExerciseSets;
import com.standofit.back.training.planning.domain.vo.WorkoutName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CreateWorkoutHandler implements CommandHandler<CreateWorkoutCommand, UUID> {

    private final WorkoutRepository repository;

    public CreateWorkoutHandler(WorkoutRepository repository) {
        this.repository = repository;
    }

    @Override
    public UUID handle(CreateWorkoutCommand command) {
        List<WorkoutDay> days = new ArrayList<>();

        for (CreateWorkoutCommand.DayInput dayInput : command.days()) {
            List<WorkoutExercise> exercises = new ArrayList<>();

            for (CreateWorkoutCommand.ExerciseInput exInput : dayInput.exercises()) {
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

        return repository.save(workout).getId().value();
    }
}