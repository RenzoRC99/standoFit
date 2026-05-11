package com.standofit.back.configuration.bus.event.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class MapDbApplicationEventRepository implements ApplicationEventRepository, DisposableBean {

  private static final Logger log = LoggerFactory.getLogger(MapDbApplicationEventRepository.class);

  private DB db;
  private ConcurrentMap<String, String> store;
  private final ObjectMapper mapper;

  public MapDbApplicationEventRepository() {
    this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    this.mapper.disable(
        com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    initDb();
  }

  private synchronized void initDb() {
    try {
      this.db =
          DBMaker.fileDB("data/application-events.db")
              .checksumHeaderBypass()
              .closeOnJvmShutdown()
              .make();
    } catch (Exception e) {
      log.warn("Could not open event store DB, cleaning and retrying: {}", e.getMessage());
      new java.io.File("data/application-events.db").delete();
      new java.io.File("data/application-events.db.t").delete();
      this.db =
          DBMaker.fileDB("data/application-events.db")
              .checksumHeaderBypass()
              .closeOnJvmShutdown()
              .make();
    }
    this.store = db.hashMap("events", Serializer.STRING, Serializer.STRING).createOrOpen();
  }

  @Override
  public void destroy() {
    if (db != null && !db.isClosed()) {
      db.close();
    }
  }

  @Override
  public void save(
      ApplicationEvent event, String eventType, String aggregateType, String aggregateId) {
    try {
      String id = UUID.randomUUID().toString();
      EventEntry entry =
          new EventEntry(
              id,
              eventType,
              aggregateType,
              aggregateId,
              mapper.writeValueAsString(event),
              Instant.now().toString());
      store.put(id, mapper.writeValueAsString(entry));
      db.commit();
      log.info("Stored event: {} [{}]", eventType, id);
    } catch (JsonProcessingException e) {
      log.error("Failed to serialize event: {}", eventType, e);
    }
  }

  @Override
  public List<Map<String, Object>> findAll() {
    List<Map<String, Object>> result = new ArrayList<>();
    for (String value : store.values()) {
      try {
        Map<String, Object> entry =
            mapper.readValue(
                value,
                mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class));
        result.add(entry);
      } catch (JsonProcessingException e) {
        log.error("Failed to deserialize event", e);
      }
    }
    return result;
  }

  private record EventEntry(
      String id,
      String eventType,
      String aggregateType,
      String aggregateId,
      String body,
      String occurredOn) {}
}
