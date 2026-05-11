package com.standofit.back.configuration.bus.event.store;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventHandler;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventRepository;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventStoreHandler implements ApplicationEventHandler<ApplicationEvent> {

  private final ApplicationEventRepository repository;

  public ApplicationEventStoreHandler(ApplicationEventRepository repository) {
    this.repository = repository;
  }

  @Override
  public Class<ApplicationEvent> eventType() {
    return ApplicationEvent.class;
  }

  @Override
  public void handle(ApplicationEvent event) {
    String eventType = event.getClass().getSimpleName();
    String aggregateType = extractAggregateType(eventType);
    String aggregateId = extractAggregateId(event);
    repository.save(event, eventType, aggregateType, aggregateId);
  }

  private String extractAggregateType(String eventType) {
    if (eventType.contains("Planning")) return "Workout";
    if (eventType.contains("Session")) return "Session";
    return eventType;
  }

  private String extractAggregateId(ApplicationEvent event) {
    if (event
        instanceof
        com.standofit.back.modules.training.planning.application.event.PlanningActivityEvent e) {
      return e.entityId();
    }
    if (event
        instanceof
        com.standofit.back.modules.training.execution.application.event.SessionActivityEvent e) {
      return e.sessionId();
    }
    return null;
  }
}
