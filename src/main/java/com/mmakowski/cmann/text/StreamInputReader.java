package com.mmakowski.cmann.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public final class StreamInputReader implements InputReader {
    private final BufferedReader in;

    public StreamInputReader(final InputStream in) {
        this.in = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public Optional<String> blockingReadLine() throws InterruptedException {
        try {
            return Optional.ofNullable(in.readLine());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
