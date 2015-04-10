package com.mmakowski.cmann.acceptance;

import com.mmakowski.util.Clock;

import java.util.concurrent.TimeUnit;

final class TestClock implements Clock {
    private long currentMsSinceEpoch = 0;

    @Override
    public long getCurrentMsSinceEpoch() {
        return currentMsSinceEpoch;
    }

    public void advance(final int time, final TimeUnit timeUnit) {
        currentMsSinceEpoch += timeUnit.toMillis(time);
    }
}
