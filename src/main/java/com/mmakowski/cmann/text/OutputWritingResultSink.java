package com.mmakowski.cmann.text;

import com.mmakowski.cmann.exec.ResultSink;
import com.mmakowski.cmann.model.Result;

public final class OutputWritingResultSink implements ResultSink {
    public OutputWritingResultSink(final OutputWriter out, final MessageFormat messageFormat) {
    }

    @Override
    public void receive(final Result result) {

    }
}
