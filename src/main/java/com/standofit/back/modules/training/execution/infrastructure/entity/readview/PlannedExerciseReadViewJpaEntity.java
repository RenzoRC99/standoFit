package com.standofit.back.modules.training.execution.infrastructure.entity.readview;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "planned_exercise_read_view")
public class PlannedExerciseReadViewJpaEntity {

  @Id
  @Column(name = "id", columnDefinition = "uuid")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_id", nullable = false)
  private SessionReadViewJpaEntity session;

  @Column(name = "exercise_id", nullable = false)
  private UUID exerciseId;

  @Column(name = "sets", nullable = false)
  private int sets;

  @Column(name = "reps", nullable = false)
  private int reps;

  @Column(name = "rest_seconds", nullable = false)
  private int restSeconds;

  @Column(name = "order_index", nullable = false)
  private int orderIndex;

  public PlannedExerciseReadViewJpaEntity() {}

  public PlannedExerciseReadViewJpaEntity(
      UUID id, UUID exerciseId, int sets, int reps, int restSeconds, int orderIndex) {
    this.id = id;
    this.exerciseId = exerciseId;
    this.sets = sets;
    this.reps = reps;
    this.restSeconds = restSeconds;
    this.orderIndex = orderIndex;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public SessionReadViewJpaEntity getSession() {
    return session;
  }

  public void setSession(SessionReadViewJpaEntity session) {
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

  public int getRestSeconds() {
    return restSeconds;
  }

  public void setRestSeconds(int restSeconds) {
    this.restSeconds = restSeconds;
  }

  public int getOrderIndex() {
    return orderIndex;
  }

  public void setOrderIndex(int orderIndex) {
    this.orderIndex = orderIndex;
  }
}
