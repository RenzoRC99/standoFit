package com.standofit.back.training.planning.infrastructure.bus;

import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryQueryBus implements QueryBus {

    private final Map<Class<? extends Query>, QueryHandler<?, ?>> handlers = new HashMap<>();

    public <Q extends Query, R> void register(Class<Q> queryClass, QueryHandler<Q, R> handler) {
        handlers.put(queryClass, handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R ask(Query query) {
        QueryHandler<Query, R> handler = (QueryHandler<Query, R>) handlers.get(query.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler registered for: " + query.getClass().getSimpleName());
        }
        return handler.handle(query);
    }
}