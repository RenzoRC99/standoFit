package com.standofit.back.modules.training.planning.application.command.change_workout_description;

import com.standofit.back.modules.training.planning.domain.entity.Workout;
import com.standofit.back.modules.training.planning.domain.entity.WorkoutRepository;
import com.standofit.back.modules.training.planning.domain.vo.WorkoutDescription;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class ChangeWorkoutDescriptionHandler implements CommandHandler<ChangeWorkoutDescriptionCommand, Void> {

    private final WorkoutRepository repository;

    public ChangeWorkoutDescriptionHandler(WorkoutRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<ChangeWorkoutDescriptionCommand> commandType() {
        return ChangeWorkoutDescriptionCommand.class;
    }

    @Override
    public Void handle(ChangeWorkoutDescriptionCommand command) {
        Workout workout = repository.findById(command.workoutId())
                .orElseThrow(() -> new IllegalArgumentException("Workout not found: " + command.workoutId()));

        Workout updated = workout.changeDescription(new WorkoutDescription(command.description()));

        // TODO: Publish WorkoutDescriptionChangedEvent
        // eventBus.publish(new WorkoutDescriptionChangedEvent(command.workoutId(), command.description()));

        repository.save(updated);
        return null;
    }
}
