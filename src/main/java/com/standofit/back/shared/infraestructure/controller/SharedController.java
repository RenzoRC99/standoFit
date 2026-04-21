package com.standofit.back.shared.infraestructure.controller;

import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class SharedController {

    private final QueryBus queryBus;

    protected SharedController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    protected <R> R ask(Query query) {
        return queryBus.ask(query);
    }

    protected ResponseEntity<?> success(Object data) {
        return ResponseEntity.ok(data);
    }

    protected ResponseEntity<?> error(int status, String message) {
        return ResponseEntity.status(status).body(Map.of("error", message));
    }
}