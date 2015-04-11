package com.mmakowski.cmann.assembly;

import com.mmakowski.cmann.model.Message;
import com.mmakowski.util.TestClock;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.Instant;

public final class CmAnnMessageFormatTest {
    @Test
    public void formatsMessageAccordingToPrescribedFormat() {
        final TestClock clock = TestClock.withCurrentInstant(Instant.parse("2015-04-11T12:01:00Z"));
        final Message message = new Message("User name", "Message text", clock.currentInstant());

        final Duration timeSincePosting = Duration.ofHours(13);
        clock.advance(timeSincePosting);

        final DurationFormat durationFormat = Mockito.mock(DurationFormat.class);
        final String formattedDuration = "13 hours";
        Mockito.when(durationFormat.format(timeSincePosting)).thenReturn(formattedDuration);

        final CmAnnMessageFormat format = new CmAnnMessageFormat(clock, durationFormat);

        final String formattedMessage = format.format(message);

        Assert.assertEquals("User name - Message text (13 hours)", formattedMessage);
    }
}
