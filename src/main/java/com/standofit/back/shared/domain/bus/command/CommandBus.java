package com.standofit.back.shared.domain.bus.command;

public interface CommandBus {
    public void dispatch(Command command);
}
