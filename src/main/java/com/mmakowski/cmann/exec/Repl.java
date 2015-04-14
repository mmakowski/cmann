package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.Command;

import java.util.Optional;

public final class Repl implements Runnable {
    private final CommandSource source;
    private final CommandExecutor executor;
    private final ResultSink sink;

    public Repl(final CommandSource source, final CommandExecutor executor, final ResultSink sink) {
        this.source = source;
        this.executor = executor;
        this.sink = sink;
    }

    @Override
    public void run() {
        boolean endOfInputReached = false;
        while (!endOfInputReached && !Thread.currentThread().isInterrupted()) {
            try {
                final Optional<Command> maybeCommand = source.blockingGetCommand();
                if (maybeCommand.isPresent()) sink.receive(executor.execute(maybeCommand.get()));
                else endOfInputReached = true;
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
