package com.standofit.back.configuration.bus.event.presentation;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEventRepository;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventsController {

  private final ApplicationEventRepository repository;

  public EventsController(ApplicationEventRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/api/events")
  public List<Map<String, Object>> getAllEvents() {
    return repository.findAll();
  }
}
