package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.Command;
import com.mmakowski.cmann.model.Result;
import com.mmakowski.cmann.model.Results;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public final class ReplTest {
    @Test
    public void stopsAtTheEndOfCurrentIterationWhenInterruptedFlagIsSet() throws InterruptedException {
        assertExecutionStopsWhenInterrupted(() -> Thread.currentThread().interrupt(), 2);
    }

    @Test
    public void stopsImmediatelyWhenBlockingReadThrowsInterruptedException() throws InterruptedException {
        assertExecutionStopsWhenInterrupted(() -> { throw new InterruptedException("deliberate interrupt in test"); }, 1);
    }

    private static void assertExecutionStopsWhenInterrupted(final Interrupt interrupt, final int expectedNumberOfCommandProcessed) throws InterruptedException {
        try {
            final Command command = Mockito.mock(Command.class);
            final Result result = Results.EMPTY;

            final CommandSource source = Mockito.mock(CommandSource.class);
            final Answer<Command> interruptAndReturnCommand = invocation -> {
                interrupt.execute();
                return command;
            };
            Mockito.when(source.blockingGetCommand()).thenReturn(command).then(interruptAndReturnCommand).thenReturn(command);

            final CommandExecutor executor = Mockito.mock(CommandExecutor.class);
            Mockito.when(executor.execute(command)).thenReturn(result);

            final ResultSink sink = Mockito.mock(ResultSink.class);

            final Repl repl = new Repl(source, executor, sink);
            clearInterruptedFlag();
            repl.run();

            Mockito.verify(sink, Mockito.times(expectedNumberOfCommandProcessed)).receive(result);
            Mockito.verifyNoMoreInteractions(sink);
        } finally {
            clearInterruptedFlag();
        }
    }

    private static void clearInterruptedFlag() {
        Thread.interrupted();
    }

    private interface Interrupt {
        void execute() throws InterruptedException;
    }
}
