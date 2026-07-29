package com.standofit.back.shared.domain.bus.command;

/**
 * Specialization of {@link CommandHandler} for commands that return {@link Void}.
 *
 * <p>Use this when the command has no meaningful return value — the handler only produces side
 * effects. Implement {@link #execute(Command)} instead of {@link #handle(Command)}.
 *
 * @param <C> the type of Command this handler processes (must have Void return type)
 */
public interface VoidCommandHandler<C extends Command<Void>> extends CommandHandler<C, Void> {

  void execute(C command);

  @Override
  default Void handle(C command) {
    execute(command);
    return null;
  }
}
