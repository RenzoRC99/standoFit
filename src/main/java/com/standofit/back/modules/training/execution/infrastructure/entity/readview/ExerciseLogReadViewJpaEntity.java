package com.standofit.back.modules.training.execution.infrastructure.entity.readview;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "exercise_log_read_view")
public class ExerciseLogReadViewJpaEntity {

  @Id
  @Column(name = "id", columnDefinition = "uuid")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_id", nullable = false)
  private SessionReadViewJpaEntity session;

  @Column(name = "exercise_id", nullable = false)
  private UUID exerciseId;

  @Column(name = "exercise_name")
  private String exerciseName;

  @Column(name = "muscle_group")
  private String muscleGroup;

  @Column(name = "sets", nullable = false)
  private int sets;

  @Column(name = "reps", nullable = false)
  private int reps;

  @Column(name = "weight", nullable = false)
  private int weight;

  public ExerciseLogReadViewJpaEntity() {}

  public ExerciseLogReadViewJpaEntity(
      UUID id,
      UUID exerciseId,
      String exerciseName,
      String muscleGroup,
      int sets,
      int reps,
      int weight) {
    this.id = id;
    this.exerciseId = exerciseId;
    this.exerciseName = exerciseName;
    this.muscleGroup = muscleGroup;
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

  public String getExerciseName() {
    return exerciseName;
  }

  public void setExerciseName(String exerciseName) {
    this.exerciseName = exerciseName;
  }

  public String getMuscleGroup() {
    return muscleGroup;
  }

  public void setMuscleGroup(String muscleGroup) {
    this.muscleGroup = muscleGroup;
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
