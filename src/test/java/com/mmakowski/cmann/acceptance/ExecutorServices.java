package com.mmakowski.cmann.acceptance;

import org.junit.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

final class ExecutorServices {
    private ExecutorServices() {}

    public static void shutdownSynchronously(final ExecutorService executor) {
        executor.shutdownNow();
        try {
            Assert.assertTrue("task did not terminate within one second", executor.awaitTermination(1, TimeUnit.SECONDS));
        } catch (final InterruptedException e1) {
            throw new AssertionError(e1);
        }
    }
}
