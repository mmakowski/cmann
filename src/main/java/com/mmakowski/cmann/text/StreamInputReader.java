package com.mmakowski.cmann.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class StreamInputReader implements InputReader {
    private final BufferedReader in;

    public StreamInputReader(final InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public String blockingReadLine() throws InterruptedException {
        try {
            return in.readLine();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
