package com.standofit.back.configuration.bus;

import com.standofit.back.shared.domain.bus.application_event.ApplicationEventBus;
import com.standofit.back.shared.domain.bus.application_event.ApplicationEventHandler;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusConfiguration {

  @Bean
  public ApplicationEventBus applicationEventBus(List<ApplicationEventHandler<?>> handlers) {
    return new InMemoryApplicationEventBus(handlers);
  }

  @Bean
  public ApplicationBus applicationBus(
      List<CommandHandler<?, ?>> commandHandlers, List<QueryHandler<?, ?>> queryHandlers) {
    InMemoryCommandBus commandBus = new InMemoryCommandBus(commandHandlers);
    InMemoryQueryBus queryBus = new InMemoryQueryBus(queryHandlers);
    return new ApplicationBus(commandBus, queryBus);
  }
}
