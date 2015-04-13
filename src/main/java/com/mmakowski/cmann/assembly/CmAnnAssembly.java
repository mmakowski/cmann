package com.mmakowski.cmann.assembly;

import com.mmakowski.cmann.exec.*;
import com.mmakowski.cmann.text.*;
import com.mmakowski.util.Clock;

public final class CmAnnAssembly {
    public final Runnable repl;

    public CmAnnAssembly(final InputReader in, final OutputWriter out, final Clock clock) {
        final CommandParser parser = new TextSplittingCommandParser();
        final CommandSource source = new InputParsingCommandSource(out, in, parser);

        final Store store = new H2Store();
        final CommandExecutor executor = new CmAnnCommandExecutor(store, clock);

        final DurationFormat durationFormat = new AdaptiveAgeFormat();
        final MessageFormat messageFormat = new ReadingMessageFormat(clock, durationFormat);
        final ResultSink sink = new OutputWritingResultSink(out, messageFormat);

        repl = new Repl(source, executor, sink);
    }
}
