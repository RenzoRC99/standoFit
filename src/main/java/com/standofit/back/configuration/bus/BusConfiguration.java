package com.standofit.back.configuration.bus;

import com.standofit.back.shared.domain.bus.command.CommandBus;
import com.standofit.back.shared.domain.bus.command.CommandHandler;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusConfiguration {

  @Bean
  public CommandBus commandBus(List<CommandHandler<?, ?>> handlers) {
    return new InMemoryCommandBus(handlers);
  }

  @Bean
  public QueryBus queryBus(List<QueryHandler<?, ?>> handlers) {
    return new InMemoryQueryBus(handlers);
  }

  @Bean
  public ApplicationBus applicationBus(CommandBus commandBus, QueryBus queryBus) {
    return new ApplicationBus(commandBus, queryBus);
  }
}
