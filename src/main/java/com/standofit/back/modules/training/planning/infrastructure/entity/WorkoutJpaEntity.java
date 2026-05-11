package com.standofit.back.modules.training.planning.infrastructure.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workouts")
public class WorkoutJpaEntity {

  @Id
  @Column(name = "id", columnDefinition = "uuid")
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @OneToMany(
      mappedBy = "workout",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @OrderBy("orderIndex ASC")
  private List<WorkoutDayJpaEntity> days = new ArrayList<>();

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  public WorkoutJpaEntity() {}

  public WorkoutJpaEntity(UUID id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public WorkoutJpaEntity(
      UUID id, String name, String description, Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<WorkoutDayJpaEntity> getDays() {
    return days;
  }

  public void setDays(List<WorkoutDayJpaEntity> days) {
    this.days = days;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void addDay(WorkoutDayJpaEntity day) {
    days.add(day);
    day.setWorkout(this);
  }

  public void removeDay(WorkoutDayJpaEntity day) {
    days.remove(day);
    day.setWorkout(null);
  }
}
