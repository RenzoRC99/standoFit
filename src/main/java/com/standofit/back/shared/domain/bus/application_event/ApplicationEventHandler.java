package com.standofit.back.shared.domain.bus.application_event;

import com.standofit.back.shared.domain.bus.event.EventHandler;

public interface ApplicationEventHandler<T extends ApplicationEvent> extends EventHandler<T> {}
