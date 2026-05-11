package com.standofit.back.configuration.bus;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventHandler;
import com.standofit.back.shared.domain.bus.command.CommandBus;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.shared.infrastructure.bus.command.InMemoryCommandBus;
import com.standofit.back.shared.infrastructure.bus.event.InMemoryApplicationEventBus;
import com.standofit.back.shared.infrastructure.bus.query.InMemoryQueryBus;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBusConfiguration {

  @Bean
  public CommandBus commandBus(List<CommandHandler<?, ?>> handlers) {
    return new InMemoryCommandBus(handlers);
  }

  @Bean
  public QueryBus queryBus(List<QueryHandler<?, ?>> handlers) {
    return new InMemoryQueryBus(handlers);
  }

  @Bean
  public ApplicationEventBus applicationEventBus(List<ApplicationEventHandler<?>> handlers) {
    return new InMemoryApplicationEventBus(handlers);
  }
}
