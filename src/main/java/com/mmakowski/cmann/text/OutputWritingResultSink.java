package com.mmakowski.cmann.text;

import com.mmakowski.cmann.exec.ResultSink;
import com.mmakowski.cmann.model.Message;
import com.mmakowski.cmann.model.ReadingResult;
import com.mmakowski.cmann.model.Result;

public final class OutputWritingResultSink implements ResultSink {
    private final OutputWriter out;
    private final MessageFormat messageFormat;

    public OutputWritingResultSink(final OutputWriter out, final MessageFormat messageFormat) {
        this.out = out;
        this.messageFormat = messageFormat;
    }

    @Override
    public void receive(final Result result) {
        if (result instanceof ReadingResult)
            for (final Message message : ((ReadingResult) result).messages) out.writeLine(messageFormat.apply(message));
    }
}
