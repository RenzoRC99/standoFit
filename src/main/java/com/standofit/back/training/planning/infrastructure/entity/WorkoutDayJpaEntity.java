package com.standofit.back.training.planning.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workout_days")
public class WorkoutDayJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "workoutDay", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<WorkoutExerciseJpaEntity> exercises = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private WorkoutJpaEntity workout;

    public WorkoutDayJpaEntity() {
    }

    public WorkoutDayJpaEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkoutExerciseJpaEntity> getExercises() {
        return exercises;
    }

    public void setExercises(List<WorkoutExerciseJpaEntity> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(WorkoutExerciseJpaEntity exercise) {
        exercises.add(exercise);
        exercise.setWorkoutDay(this);
    }

    public void removeExercise(WorkoutExerciseJpaEntity exercise) {
        exercises.remove(exercise);
        exercise.setWorkoutDay(null);
    }

    public WorkoutJpaEntity getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutJpaEntity workout) {
        this.workout = workout;
    }
}
