package com.mmakowski.cmann.assembly;

import java.time.Duration;

public final class AdaptiveDurationFormat implements DurationFormat {
    @Override
    public String format(final Duration duration) {
        final String formatted;
        if (isShorter(duration, Duration.ofSeconds(1))) formatted = "now";
        else if (!isShorter(duration, Duration.ofDays(1))) formatted = "ages ago";
        else formatted = formatAsNumberAndUnits(duration);
        return formatted;
    }

    private static String formatAsNumberAndUnits(final Duration duration) {
        final long number;
        final String unit;
        if (isShorter(duration, Duration.ofMinutes(1))) {
            number = duration.toMillis() / 1000;
            unit = "second";
        } else if (isShorter(duration, Duration.ofHours(1))) {
            number = duration.toMinutes();
            unit = "minute";
        } else {
            number = duration.toHours();
            unit = "hour";
        }
        return number + " " + unit + (number == 1 ? "" : "s");
    }

    private static boolean isShorter(final Duration duration1, final Duration duration2) {
        return duration1.compareTo(duration2) == -1;
    }
}
