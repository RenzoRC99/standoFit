package com.standofit.back.modules.training.planning.infrastructure.bus;

import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryQueryBus implements QueryBus {

    private final Map<Class<?>, QueryHandler<?, ?>> handlers;

    public InMemoryQueryBus(List<QueryHandler<?, ?>> handlers) {
        this.handlers = new HashMap<>();

        for (QueryHandler<?, ?> handler : handlers) {
            Class<?> type = handler.queryType();

            if (this.handlers.containsKey(type)) {
                throw new IllegalStateException(
                        "Duplicate handler for " + type.getSimpleName()
                );
            }

            this.handlers.put(type, handler);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public <R> R ask(Query<R> query) {

        QueryHandler<Query<R>, R> handler =
                (QueryHandler<Query<R>, R>) handlers.get(query.getClass());

        if (handler == null) {
            throw new IllegalStateException(
                    "No handler registered for query: " + query.getClass().getSimpleName()
            );
        }

        return handler.handle(query);
    }
}
