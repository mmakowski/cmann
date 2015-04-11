package com.mmakowski.cmann.assembly;

import com.mmakowski.cmann.model.Result;

public interface ResultSink {
    void receive(Result result);
}
