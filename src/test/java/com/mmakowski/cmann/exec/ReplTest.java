package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.Command;
import com.mmakowski.cmann.model.Result;
import com.mmakowski.cmann.model.Results;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.Optional;

public final class ReplTest {
    @Test
    public void stopsAtTheEndOfCurrentIterationWhenInterruptedFlagIsSet() throws InterruptedException {
        assertExecutionStopsWhenInterrupted(() -> Thread.currentThread().interrupt(), 2);
    }

    @Test
    public void stopsImmediatelyWhenBlockingReadThrowsInterruptedException() throws InterruptedException {
        assertExecutionStopsWhenInterrupted(() -> { throw new InterruptedException("deliberate interrupt in test"); }, 1);
    }

    @Test
    public void stopsWhenNoCommandIsReturned() throws InterruptedException {
        final CommandSource source = Mockito.mock(CommandSource.class);
        Mockito.when(source.blockingGetCommand()).thenReturn(maybeCommand).thenReturn(Optional.empty());

        final ResultSink sink = Mockito.mock(ResultSink.class);

        final Repl repl = new Repl(source, mockExecutor(), sink);
        repl.run();

        Mockito.verify(sink, Mockito.times(1)).receive(result);
        Mockito.verifyNoMoreInteractions(sink);
    }

    private static void assertExecutionStopsWhenInterrupted(final Interrupt interrupt, final int expectedNumberOfCommandProcessed) throws InterruptedException {
        try {
            final CommandSource source = Mockito.mock(CommandSource.class);
            final Answer<Optional<Command>> interruptAndReturnCommand = invocation -> {
                interrupt.execute();
                return maybeCommand;
            };
            Mockito.when(source.blockingGetCommand()).thenReturn(maybeCommand).then(interruptAndReturnCommand).thenReturn(maybeCommand);

            final ResultSink sink = Mockito.mock(ResultSink.class);

            final Repl repl = new Repl(source, mockExecutor(), sink);
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

    private static CommandExecutor mockExecutor() {
        final CommandExecutor executor = Mockito.mock(CommandExecutor.class);
        Mockito.when(executor.execute(command)).thenReturn(result);
        return executor;
    }

    private interface Interrupt {
        void execute() throws InterruptedException;
    }

    private static final Command command = new Command() {};
    private static final Optional<Command> maybeCommand = Optional.of(command);
    private static final Result result = Results.EMPTY;
}
