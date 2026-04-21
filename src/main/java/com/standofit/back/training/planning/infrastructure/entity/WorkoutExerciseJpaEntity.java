package com.standofit.back.training.planning.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "workout_exercises")
public class WorkoutExerciseJpaEntity {

  @Id
  @Column(name = "id", columnDefinition = "uuid")
  private UUID id;

  @Column(name = "exercise_id", nullable = false, columnDefinition = "uuid")
  private UUID exerciseId;

  @Column(name = "sets", nullable = false)
  private Integer sets;

  @Column(name = "reps", nullable = false)
  private Integer reps;

  @Column(name = "rest_seconds", nullable = false)
  private Integer restSeconds;

  @ManyToOne
  @JoinColumn(name = "workout_day_id")
  private WorkoutDayJpaEntity workoutDay;

  public WorkoutExerciseJpaEntity() {}

  public WorkoutExerciseJpaEntity(
      UUID id, UUID exerciseId, Integer sets, Integer reps, Integer restSeconds) {
    this.id = id;
    this.exerciseId = exerciseId;
    this.sets = sets;
    this.reps = reps;
    this.restSeconds = restSeconds;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getExerciseId() {
    return exerciseId;
  }

  public void setExerciseId(UUID exerciseId) {
    this.exerciseId = exerciseId;
  }

  public Integer getSets() {
    return sets;
  }

  public void setSets(Integer sets) {
    this.sets = sets;
  }

  public Integer getReps() {
    return reps;
  }

  public void setReps(Integer reps) {
    this.reps = reps;
  }

  public Integer getRestSeconds() {
    return restSeconds;
  }

  public void setRestSeconds(Integer restSeconds) {
    this.restSeconds = restSeconds;
  }

  public WorkoutDayJpaEntity getWorkoutDay() {
    return workoutDay;
  }

  public void setWorkoutDay(WorkoutDayJpaEntity workoutDay) {
    this.workoutDay = workoutDay;
  }
}
