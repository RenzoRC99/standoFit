package com.standofit.back.configuration.bus.event.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventRepository;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MapDbApplicationEventRepository implements ApplicationEventRepository {

  private static final Logger log = LoggerFactory.getLogger(MapDbApplicationEventRepository.class);

  private final DB db;
  private final ConcurrentMap<String, String> store;
  private final ObjectMapper mapper;

  public MapDbApplicationEventRepository() {
    this.db =
        DBMaker.fileDB("data/application-events.db").fileMmapEnable().closeOnJvmShutdown().make();
    this.store = db.hashMap("events", Serializer.STRING, Serializer.STRING).createOrOpen();
    this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    this.mapper.disable(
        com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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

  private record EventEntry(
      String id,
      String eventType,
      String aggregateType,
      String aggregateId,
      String body,
      String occurredOn) {}
}
