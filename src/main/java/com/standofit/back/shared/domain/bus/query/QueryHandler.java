package com.standofit.back.shared.domain.bus.query;

public interface QueryHandler<Q extends Query, R> {
    R handle(Q query);
}