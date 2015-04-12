package com.mmakowski.cmann.text;

import com.mmakowski.cmann.model.Message;
import com.mmakowski.util.Clock;

import java.time.Duration;

public final class ReadingMessageFormat implements MessageFormat {
    private final Clock clock;
    private final DurationFormat durationFormat;

    public ReadingMessageFormat(final Clock clock, final DurationFormat durationFormat) {
        this.clock = clock;
        this.durationFormat = durationFormat;
    }

    public String apply(final Message message) {
        final String formattedAge = durationFormat.apply(Duration.between(message.timestamp, clock.currentInstant()));
        return message.message + " (" + formattedAge + ")";
    }
}
