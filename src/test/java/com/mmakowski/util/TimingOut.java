package com.mmakowski.util;

import java.time.Duration;
import java.util.concurrent.*;

public final class TimingOut {
    private TimingOut() {}

    public static <T> T execute(final Block<T> block, final Duration timeout) throws TimeoutException {
        final ExecutorService asyncExecutor = Executors.newSingleThreadExecutor();
        try {
            final Future<T> future = asyncExecutor.submit(block::execute);
            return future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            throw new AssertionError(e);
        } finally {
            asyncExecutor.shutdownNow();
        }
    }

    public interface Block<T> {
        T execute() throws Exception;
    }
}
