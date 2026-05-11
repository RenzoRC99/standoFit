package com.standofit.back.shared.domain.bus.application_event;

public interface ApplicationEventRepository {
  void save(ApplicationEvent event, String eventType, String aggregateType, String aggregateId);
}
