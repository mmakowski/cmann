package com.mmakowski.cmann.text;

import java.io.InputStream;

public final class StreamInputReader implements InputReader {
    public StreamInputReader(InputStream in) {
    }

    @Override
    public String blockingReadLine() throws InterruptedException {
        return null;
    }
}
