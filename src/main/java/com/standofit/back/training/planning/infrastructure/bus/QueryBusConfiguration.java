package com.standofit.back.training.planning.infrastructure.bus;

import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Configuration
public class QueryBusConfiguration {

    public InMemoryQueryBus queryBus(List<QueryHandler<?, ?>> handlers) {
        Map<Class<? extends Query>, QueryHandler<?, ?>> handlerMap = handlers.stream()
                .collect(toMap(this::extractQueryType, identity()));

        InMemoryQueryBus queryBus = new InMemoryQueryBus();
        handlerMap.forEach((clazz, h) -> queryBus.register(clazz, (QueryHandler) h));
        return queryBus;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Query> extractQueryType(QueryHandler<?, ?> handler) {
        try {
            var interfaces = handler.getClass().getInterfaces();
            for (var iface : interfaces) {
                if (iface.getSimpleName().startsWith("QueryHandler")) {
                    var typeArgs = ((java.lang.reflect.ParameterizedType) iface.getGenericInterfaces()[0]).getActualTypeArguments();
                    return (Class<? extends Query>) typeArgs[0];
                }
            }
            throw new RuntimeException("Could not extract query type from: " + handler.getClass());
        } catch (Exception e) {
            throw new RuntimeException("Failed to determine query type for handler: " + handler.getClass(), e);
        }
    }
}