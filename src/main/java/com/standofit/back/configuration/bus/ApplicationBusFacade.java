package com.standofit.back.configuration.bus;

import com.standofit.back.shared.domain.bus.command.Command;
import com.standofit.back.shared.domain.bus.command.CommandBus;
import com.standofit.back.shared.domain.bus.query.Query;
import com.standofit.back.shared.domain.bus.query.QueryBus;
import org.springframework.stereotype.Component;

@Component
public class ApplicationBusFacade {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    public ApplicationBusFacade(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    public <R> R execute(Command<R> command) {
        return commandBus.dispatch(command);
    }

    public <R> R ask(Query<R> query) {
        return queryBus.ask(query);
    }
}
