package com.standofit.back.modules.training.planning.infrastructure.entity;

import jakarta.persistence.*;
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

  @Column(name = "order_index")
  private int orderIndex;

  @OneToMany(
      mappedBy = "workoutDay",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private List<WorkoutExerciseJpaEntity> exercises = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "workout_id")
  private WorkoutJpaEntity workout;

  public WorkoutDayJpaEntity() {}

  public WorkoutDayJpaEntity(UUID id, String name, int orderIndex) {
    this.id = id;
    this.name = name;
    this.orderIndex = orderIndex;
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

  public int getOrderIndex() {
    return orderIndex;
  }

  public void setOrderIndex(int orderIndex) {
    this.orderIndex = orderIndex;
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
