package com.standofit.back.shared.domain.bus.command;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEvent;

public interface EventfulCommand {
  ApplicationEvent toSuccessEvent();

  ApplicationEvent toFailureEvent(String errorDetail);
}
