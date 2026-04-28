package com.standofit.back.modules.training.execution.infrastructure.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "exercise_logs")
public class ExerciseLogJpaEntity {

  @Id
  @Column(name = "id", columnDefinition = "uuid")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_id", nullable = false)
  private SessionJpaEntity session;

  @Column(name = "exercise_id", nullable = false)
  private UUID exerciseId;

  @Column(name = "sets", nullable = false)
  private int sets;

  @Column(name = "reps", nullable = false)
  private int reps;

  @Column(name = "weight", nullable = false)
  private int weight;

  public ExerciseLogJpaEntity() {}

  public ExerciseLogJpaEntity(UUID id, UUID exerciseId, int sets, int reps, int weight) {
    this.id = id;
    this.exerciseId = exerciseId;
    this.sets = sets;
    this.reps = reps;
    this.weight = weight;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public SessionJpaEntity getSession() {
    return session;
  }

  public void setSession(SessionJpaEntity session) {
    this.session = session;
  }

  public UUID getExerciseId() {
    return exerciseId;
  }

  public void setExerciseId(UUID exerciseId) {
    this.exerciseId = exerciseId;
  }

  public int getSets() {
    return sets;
  }

  public void setSets(int sets) {
    this.sets = sets;
  }

  public int getReps() {
    return reps;
  }

  public void setReps(int reps) {
    this.reps = reps;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }
}
