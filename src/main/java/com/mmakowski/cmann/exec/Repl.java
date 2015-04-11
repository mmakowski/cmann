package com.mmakowski.cmann.exec;

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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sink.receive(executor.execute(source.blockingGetCommand()));
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
