package com.mmakowski.cmann.acceptance;

import com.google.common.collect.ImmutableList;
import com.mmakowski.cmann.text.InputReader;
import com.mmakowski.util.TimingOut;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

final class TimeoutBasedAvailableLinesReader {
    private final Duration maxLineReadDelay;

    public static TimeoutBasedAvailableLinesReader withReadTimeout(final Duration maxLineReadDelay) {
        return new TimeoutBasedAvailableLinesReader(maxLineReadDelay);
    }

    private TimeoutBasedAvailableLinesReader(final Duration maxLineReadDelay) {
        this.maxLineReadDelay = maxLineReadDelay;
    }

    public List<String> readAvailableLines(final InputReader reader) {
        boolean endOfAvailableOutputReached = false;
        final ImmutableList.Builder<String> readLines = ImmutableList.builder();
        while (!endOfAvailableOutputReached) {
            try {
                readLines.add(TimingOut.execute((TimingOut.Block<String>) reader::blockingReadLine, maxLineReadDelay));
            } catch (final TimeoutException e) {
                endOfAvailableOutputReached = true;
            }
        }
        return readLines.build();
    }
}
