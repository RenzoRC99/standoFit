package com.standofit.back.training.planning.infrastructure.bus;

import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import com.standofit.back.training.planning.application.query.GetWorkoutByIdQuery;
import com.standofit.back.training.planning.application.query.GetWorkoutByIdHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryBusConfiguration {

    @Bean
    public QueryBus queryBus(GetWorkoutByIdHandler getWorkoutByIdHandler) {
        InMemoryQueryBus queryBus = new InMemoryQueryBus();
        queryBus.register(GetWorkoutByIdQuery.class, getWorkoutByIdHandler);
        return queryBus;
    }
}