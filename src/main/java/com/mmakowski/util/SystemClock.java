package com.mmakowski.util;

import java.time.Instant;

public final class SystemClock implements Clock {
    @Override
    public Instant currentInstant() {
        return Instant.now();
    }
}
