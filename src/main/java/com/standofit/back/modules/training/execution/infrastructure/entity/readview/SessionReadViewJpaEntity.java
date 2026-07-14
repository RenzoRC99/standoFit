package com.standofit.back.modules.training.execution.infrastructure.entity.readview;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "session_read_view")
public class SessionReadViewJpaEntity {

  @Id
  @Column(name = "id", columnDefinition = "uuid")
  private UUID id;

  @Column(name = "day_id", nullable = false)
  private UUID dayId;

  @Column(name = "status", nullable = false)
  private String status;

  @OneToMany(
      mappedBy = "session",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  private List<ExerciseLogReadViewJpaEntity> logs = new ArrayList<>();

  @Column(name = "notes")
  private String notes;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  public SessionReadViewJpaEntity() {}

  public SessionReadViewJpaEntity(
      UUID id, UUID dayId, String status, String notes, Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.dayId = dayId;
    this.status = status;
    this.notes = notes;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getDayId() {
    return dayId;
  }

  public void setDayId(UUID dayId) {
    this.dayId = dayId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<ExerciseLogReadViewJpaEntity> getLogs() {
    return logs;
  }

  public void setLogs(List<ExerciseLogReadViewJpaEntity> logs) {
    this.logs = logs;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
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

  public void addLog(ExerciseLogReadViewJpaEntity log) {
    logs.add(log);
    log.setSession(this);
  }
}
