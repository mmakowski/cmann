package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.Result;

public interface ResultSink {
    void receive(Result result);
}
