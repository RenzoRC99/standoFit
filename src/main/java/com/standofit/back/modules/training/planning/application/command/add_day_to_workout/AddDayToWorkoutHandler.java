package com.standofit.back.modules.training.planning.application.command.add_day_to_workout;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutDay;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutExercise;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutDayName;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseReps;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseRest;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutExerciseSets;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutDayId;
import com.standofit.back.shared.domain.valueobjects.ids.WorkoutExerciseId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AddDayToWorkoutHandler implements CommandHandler<AddDayToWorkoutCommand, Void> {

    private final WorkoutRepository repository;

    public AddDayToWorkoutHandler(WorkoutRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<AddDayToWorkoutCommand> commandType() {
        return AddDayToWorkoutCommand.class;
    }

    @Override
    public Void handle(AddDayToWorkoutCommand command) {
        Workout workout = repository.findById(command.workoutId())
                .orElseThrow(() -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

        List<WorkoutExercise> exercises = new ArrayList<>();
        for (AddDayToWorkoutCommand.ExerciseInput exInput : command.exercises()) {
            WorkoutExercise exercise = WorkoutExercise.create(
                    new WorkoutExerciseId(UUID.randomUUID()),
                    new ExerciseId(exInput.exerciseId()),
                    new WorkoutExerciseSets(exInput.sets()),
                    new WorkoutExerciseReps(exInput.reps()),
                    new WorkoutExerciseRest(exInput.restSeconds())
            );
            exercises.add(exercise);
        }

        WorkoutDay newDay = WorkoutDay.create(
                new WorkoutDayId(UUID.randomUUID()),
                new WorkoutDayName(command.dayName()),
                exercises
        );

        Workout updatedWorkout = workout.addDays(List.of(newDay));

        // TODO: Publish DayAddedToWorkoutEvent
        // eventBus.publish(new DayAddedToWorkoutEvent(workout.getId(), newDay.getId()));

        repository.save(updatedWorkout);

        return null;
    }
}
