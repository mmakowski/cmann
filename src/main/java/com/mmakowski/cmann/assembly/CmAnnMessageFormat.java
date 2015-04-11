package com.mmakowski.cmann.assembly;

import com.mmakowski.cmann.model.Message;
import com.mmakowski.util.Clock;

import java.time.Duration;

public final class CmAnnMessageFormat implements MessageFormat {
    private final Clock clock;
    private final DurationFormat durationFormat;

    public CmAnnMessageFormat(final Clock clock, final DurationFormat durationFormat) {
        this.clock = clock;
        this.durationFormat = durationFormat;
    }

    public String format(final Message message) {
        final String formattedAge = durationFormat.format(Duration.between(message.timestamp, clock.currentInstant()));
        return message.userName + " - " + message.message + " (" + formattedAge + ")";
    }
}
