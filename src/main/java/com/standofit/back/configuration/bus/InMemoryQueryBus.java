package com.standofit.back.configuration.bus;

import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import com.standofit.back.shared.domain.bus.query.QueryHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryQueryBus implements QueryBus {

  private final Map<Class<?>, QueryHandler<?, ?>> handlers;

  public InMemoryQueryBus(List<QueryHandler<?, ?>> handlers) {
    this.handlers = new HashMap<>();

    for (QueryHandler<?, ?> handler : handlers) {
      Class<?> type = handler.queryType();

      if (this.handlers.containsKey(type)) {
        throw new BusException(BusErrors.DUPLICATE_QUERY_HANDLER.getMessage(type.getSimpleName()));
      }

      this.handlers.put(type, handler);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <R> R ask(Query<R> query) {

    QueryHandler<Query<R>, R> handler = (QueryHandler<Query<R>, R>) handlers.get(query.getClass());

    if (handler == null) {
      throw new BusException(
          BusErrors.QUERY_HANDLER_NOT_FOUND.getMessage(query.getClass().getSimpleName()));
    }

    return handler.handle(query);
  }
}
