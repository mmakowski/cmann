package com.mmakowski.util;

import java.time.Instant;

public interface Clock {
    Instant currentInstant();
}
