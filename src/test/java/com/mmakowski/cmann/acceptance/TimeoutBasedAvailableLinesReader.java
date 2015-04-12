package com.mmakowski.cmann.acceptance;

import com.google.common.collect.ImmutableList;
import com.mmakowski.cmann.text.InputReader;

import java.util.List;
import java.util.concurrent.*;

final class TimeoutBasedAvailableLinesReader implements AutoCloseable {
    private final int maxLineReadDelayMs;

    private ExecutorService asyncExecutor = Executors.newSingleThreadExecutor();

    public static TimeoutBasedAvailableLinesReader withReadTimeoutMs(final int maxLineReadDelayMs) {
        return new TimeoutBasedAvailableLinesReader(maxLineReadDelayMs);
    }

    private TimeoutBasedAvailableLinesReader(final int maxLineReadDelayMs) {
        this.maxLineReadDelayMs = maxLineReadDelayMs;
    }

    public List<String> readAvailableLines(final InputReader reader) {
        boolean endOfAvailableOutputReached = false;
        final ImmutableList.Builder<String> readLines = ImmutableList.builder();
        while (!endOfAvailableOutputReached) {
            try {
                final Future<String> future = asyncExecutor.submit((Callable<String>) reader::blockingReadLine);
                readLines.add(future.get(maxLineReadDelayMs, TimeUnit.MILLISECONDS));
            } catch (InterruptedException | ExecutionException e) {
                throw new AssertionError(e);
            } catch (final TimeoutException e) {
                endOfAvailableOutputReached = true;
                ExecutorServices.shutdownSynchronously(asyncExecutor);
                asyncExecutor = Executors.newSingleThreadExecutor();
            }
        }
        return readLines.build();
    }

    @Override
    public void close() {
        ExecutorServices.shutdownSynchronously(asyncExecutor);
    }
}
