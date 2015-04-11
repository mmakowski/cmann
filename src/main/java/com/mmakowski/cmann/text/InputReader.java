package com.mmakowski.cmann.text;

public interface InputReader {
    String blockingReadLine() throws InterruptedException;
}
