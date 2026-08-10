package com.standofit.back.modules.training.execution.infrastructure.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.standofit.back.modules.training.execution.domain.entity.ExerciseLog;
import com.standofit.back.modules.training.execution.domain.event.*;
import com.standofit.back.modules.training.execution.domain.vo.*;
import com.standofit.back.modules.training.execution.infrastructure.SerializationException;
import com.standofit.back.shared.domain.bus.event.DomainEvent;
import com.standofit.back.shared.domain.valueobjects.ids.ExerciseId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionDayId;
import com.standofit.back.shared.domain.valueobjects.ids.SessionId;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class DomainEventSerializer {

  private final ObjectMapper mapper;
  private final Map<String, EventFactory> factories;

  public DomainEventSerializer() {
    this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    this.factories = buildFactories();
  }

  public EventRow serialize(DomainEvent event, String aggregateType) {
    Map<String, Object> payload = extractPayload(event);
    try {
      return new EventRow(
          event.eventId(),
          event.aggregateId().value(),
          aggregateType,
          event.StringName(),
          event.getClass().getName(),
          event.occurredOn(),
          payload.isEmpty() ? "{}" : mapper.writeValueAsString(payload));
    } catch (JsonProcessingException e) {
      throw new SerializationException("Failed to serialize event " + event.StringName(), e);
    }
  }

  public DomainEvent deserialize(EventRow row) {
    EventFactory factory = factories.get(row.eventName());
    if (factory == null) {
      throw new SerializationException("Unknown event name: " + row.eventName());
    }
    try {
      JsonNode payload = mapper.readTree(row.payload());
      return factory.create(row.aggregateId(), payload);
    } catch (JsonProcessingException e) {
      throw new SerializationException("Failed to deserialize event " + row.eventName(), e);
    }
  }

  private Map<String, Object> extractPayload(DomainEvent event) {
    Map<String, Object> payload = new LinkedHashMap<>();

    if (event instanceof SessionStarted s) {
      payload.put("dayId", s.dayId().value().toString());
    } else if (event instanceof SessionNotesChanged n) {
      payload.put("notes", n.notes().value());
    } else if (event instanceof ExerciseLogAdded a) {
      ExerciseLog log = a.log();
      Map<String, Object> logMap = new LinkedHashMap<>();
      logMap.put("id", log.getId().value().toString());
      logMap.put("exerciseId", log.getExerciseId().value().toString());
      logMap.put("sets", log.getSets().value());
      logMap.put("reps", log.getReps().value());
      logMap.put("weight", log.getWeight().value());
      payload.put("log", logMap);
    } else if (event instanceof ExerciseLogRemoved r) {
      payload.put("logId", r.logId().value().toString());
    } else if (event instanceof ExerciseLogSetsUpdated u) {
      payload.put("logId", u.logId().value().toString());
      payload.put("sets", u.sets().value());
    } else if (event instanceof ExerciseLogRepsUpdated u) {
      payload.put("logId", u.logId().value().toString());
      payload.put("reps", u.reps().value());
    } else if (event instanceof ExerciseLogWeightUpdated u) {
      payload.put("logId", u.logId().value().toString());
      payload.put("weight", u.weight().value());
    }
    // SessionFinished, SessionCancelled → empty payload

    return payload;
  }

  @SuppressWarnings("java:S6204")
  private Map<String, EventFactory> buildFactories() {
    return Map.ofEntries(
        Map.entry(
            "session.started",
            (aggregateId, payload) -> {
              UUID dayId = UUID.fromString(payload.get("dayId").asText());
              return new SessionStarted(new SessionId(aggregateId), new SessionDayId(dayId));
            }),
        Map.entry(
            "session.finished",
            (aggregateId, payload) -> new SessionFinished(new SessionId(aggregateId))),
        Map.entry(
            "session.cancelled",
            (aggregateId, payload) -> new SessionCancelled(new SessionId(aggregateId))),
        Map.entry(
            "session.notes_changed",
            (aggregateId, payload) -> {
              String notes = payload.get("notes").asText();
              return new SessionNotesChanged(
                  new SessionId(aggregateId), new WorkoutSessionNotes(notes));
            }),
        Map.entry(
            "session.exercise_log_added",
            (aggregateId, payload) -> {
              JsonNode logNode = payload.get("log");
              ExerciseLog log =
                  ExerciseLog.create(
                      new ExerciseLogId(UUID.fromString(logNode.get("id").asText())),
                      new ExerciseId(UUID.fromString(logNode.get("exerciseId").asText())),
                      new ExerciseLogSets(logNode.get("sets").asInt()),
                      new ExerciseLogReps(logNode.get("reps").asInt()),
                      new ExerciseLogWeight(logNode.get("weight").asInt()));
              return new ExerciseLogAdded(new SessionId(aggregateId), log);
            }),
        Map.entry(
            "session.exercise_log_removed",
            (aggregateId, payload) -> {
              UUID logId = UUID.fromString(payload.get("logId").asText());
              return new ExerciseLogRemoved(new SessionId(aggregateId), new ExerciseLogId(logId));
            }),
        Map.entry(
            "session.exercise_log_sets_updated",
            (aggregateId, payload) -> {
              UUID logId = UUID.fromString(payload.get("logId").asText());
              int sets = payload.get("sets").asInt();
              return new ExerciseLogSetsUpdated(
                  new SessionId(aggregateId), new ExerciseLogId(logId), new ExerciseLogSets(sets));
            }),
        Map.entry(
            "session.exercise_log_reps_updated",
            (aggregateId, payload) -> {
              UUID logId = UUID.fromString(payload.get("logId").asText());
              int reps = payload.get("reps").asInt();
              return new ExerciseLogRepsUpdated(
                  new SessionId(aggregateId), new ExerciseLogId(logId), new ExerciseLogReps(reps));
            }),
        Map.entry(
            "session.exercise_log_weight_updated",
            (aggregateId, payload) -> {
              UUID logId = UUID.fromString(payload.get("logId").asText());
              int weight = payload.get("weight").asInt();
              return new ExerciseLogWeightUpdated(
                  new SessionId(aggregateId),
                  new ExerciseLogId(logId),
                  new ExerciseLogWeight(weight));
            }));
  }

  @FunctionalInterface
  private interface EventFactory {
    DomainEvent create(UUID aggregateId, JsonNode payload);
  }
}
