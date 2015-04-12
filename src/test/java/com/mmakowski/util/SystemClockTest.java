package com.mmakowski.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

public final class SystemClockTest {
    @Test
    public void returnsTheSystemTime() throws InterruptedException {
        final Duration timingTolerance = Duration.ofMillis(50);
        final SystemClock clock = new SystemClock();

        assertWithinTolerance(timingTolerance, clock.currentInstant(), Instant.now());
        Thread.sleep(timingTolerance.toMillis() * 2);
        assertWithinTolerance(timingTolerance, clock.currentInstant(), Instant.now());
    }

    private static void assertWithinTolerance(final Duration tolerance, final Instant instant1, final Instant instant2) {
        final Duration actual = Duration.between(instant1, instant2).abs();
        if (actual.compareTo(tolerance) > 0) Assert.fail("time difference of " + actual.toMillis() + "ms was larger than allowed " + tolerance.toMillis() + "ms");
    }
}
