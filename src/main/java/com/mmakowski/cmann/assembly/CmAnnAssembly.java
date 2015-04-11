package com.mmakowski.cmann.assembly;

import com.mmakowski.util.Clock;

public class CmAnnAssembly implements Runnable {
    private final InputReader in;
    private final OutputWriter out;
    private final Clock clock;

    public CmAnnAssembly(final InputReader in, final OutputWriter out, final Clock clock) {
        this.in = in;
        this.out = out;
        this.clock = clock;
    }

    @Override
    public void run() {
        final CommandParser parser = new CommandParser();
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(clock);
        while (!Thread.currentThread().isInterrupted()) {
            out.writeLine(">");
            try {
                final Result result = executor.execute(parser.parse(in.blockingReadLine()));
                for (final Message message : result.messages) out.writeLine(message.userName + " - " + message.message + " ()");
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

