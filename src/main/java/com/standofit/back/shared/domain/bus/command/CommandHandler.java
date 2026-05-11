package com.standofit.back.shared.domain.bus.command;

/**
 * Base interface for all Command Handlers.
 *
 * @param <C> the type of Command this handler processes
 * @param <R> the return type — use Void for commands without return value
 */
public interface CommandHandler<C extends Command<R>, R> {
  Class<C> commandType();

  R handle(C command);
}
