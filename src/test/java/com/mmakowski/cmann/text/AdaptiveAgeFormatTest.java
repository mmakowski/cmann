package com.mmakowski.cmann.text;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public final class AdaptiveAgeFormatTest {
    @Test
    public void formatsAsNowIfShorterThanSecond() {
        assertFormat("now", Duration.ofMillis(999));
    }

    @Test
    public void formatsAsSecondsIfShorterThanMinute() {
        assertFormat("59 seconds ago", Duration.ofSeconds(59));
    }

    @Test
    public void formatsAsMinutesIfShortenThanHour() {
        assertFormat("59 minutes ago", Duration.ofMinutes(59));
    }

    @Test
    public void formatsAsHoursIfShortenThanDay() {
        assertFormat("23 hours ago", Duration.ofHours(23));
    }

    @Test
    public void formatsAsAgesAgoIfNotShorterThanDay() {
        assertFormat("ages ago", Duration.ofDays(1));
    }

    @Test
    public void discardsFractionalPartsOfTimeUnit() {
        assertFormat("2 seconds ago", Duration.ofMillis(2400));
        assertFormat("2 minutes ago", Duration.ofSeconds(150));
        assertFormat("2 hours ago", Duration.ofMinutes(150));
    }

    @Test
    public void usesSingularTimeUnitIfNumberIsOne() {
        assertFormat("1 second ago", Duration.ofSeconds(1));
        assertFormat("1 minute ago", Duration.ofMinutes(1));
        assertFormat("1 hour ago", Duration.ofHours(1));
    }

    private static void assertFormat(final String expected, final Duration duration) {
        Assert.assertEquals(expected, new AdaptiveAgeFormat().apply(duration));
    }
}
