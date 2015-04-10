package com.mmakowski.util;

import java.time.Duration;
import java.time.Instant;

public final class TestClock implements Clock {
    private volatile Instant currentInstant;

    public static TestClock withCurrentInstant(final Instant instant) {
        return new TestClock(instant);
    }

    public TestClock() {
        this(Instant.EPOCH);
    }

    private TestClock(final Instant instant) {
        currentInstant = instant;
    }

    @Override
    public Instant currentInstant() {
        return currentInstant;
    }

    public void advance(final Duration duration) {
        currentInstant = currentInstant.plusMillis(duration.toMillis());
    }
}
