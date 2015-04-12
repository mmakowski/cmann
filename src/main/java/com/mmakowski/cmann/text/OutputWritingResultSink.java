package com.mmakowski.cmann.text;

import com.mmakowski.cmann.exec.ResultSink;
import com.mmakowski.cmann.model.Message;
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
        for (final Message message : result.messages) out.writeLine(messageFormat.apply(message));
    }
}
