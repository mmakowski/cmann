package com.mmakowski.cmann.text;

import com.mmakowski.cmann.model.Message;
import com.mmakowski.util.TestClock;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.Instant;

public final class WallMessageFormatTest {
    @Test
    public void formatsMessageAccordingToPrescribedWallFormat() {
        final TestClock clock = TestClock.withCurrentInstant(Instant.parse("2015-04-11T12:01:00Z"));
        final Message message = new Message("User name", "Message text", clock.currentInstant());

        final Duration timeSincePosting = Duration.ofHours(13);
        clock.advance(timeSincePosting);

        final DurationFormat durationFormat = Mockito.mock(DurationFormat.class);
        final String formattedDuration = "13 hours";
        Mockito.when(durationFormat.apply(timeSincePosting)).thenReturn(formattedDuration);

        final WallMessageFormat format = new WallMessageFormat(clock, durationFormat);

        final String formattedMessage = format.apply(message);

        Assert.assertEquals("User name - Message text (13 hours)", formattedMessage);
    }
}
