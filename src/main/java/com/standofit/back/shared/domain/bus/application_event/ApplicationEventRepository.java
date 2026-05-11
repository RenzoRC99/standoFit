package com.standofit.back.shared.domain.bus.application_event;

import java.util.List;
import java.util.Map;

public interface ApplicationEventRepository {
  void save(ApplicationEvent event, String eventType, String aggregateType, String aggregateId);

  List<Map<String, Object>> findAll();
}
