package com.mmakowski.cmann.text;

import com.mmakowski.cmann.exec.ResultSink;
import com.mmakowski.cmann.model.Message;
import com.mmakowski.cmann.model.ReadingResult;
import com.mmakowski.cmann.model.Result;
import com.mmakowski.cmann.model.WallResult;

public final class OutputWritingResultSink implements ResultSink {
    private final OutputWriter out;
    private final MessageFormat readingFormat;
    private final MessageFormat wallFormat;

    public OutputWritingResultSink(final OutputWriter out, final MessageFormat readingFormat, final MessageFormat wallFormat) {
        this.out = out;
        this.readingFormat = readingFormat;
        this.wallFormat = wallFormat;
    }

    @Override
    public void receive(final Result result) {
        final MessageFormat format = (result instanceof ReadingResult) ? readingFormat : wallFormat;
        for (final Message message : (result.messages())) out.writeLine(format.apply(message));
    }
}
