package com.mmakowski.cmann.assembly;

import com.mmakowski.util.TestClock;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

public final class CommandExecutorTest {
    private static final Posting testPosting = new Posting("Alice", "I love the weather today");

    @Test
    public void postingResultsInNoOutput() {
        final CommandExecutor executor = new CommandExecutor(new TestClock());
        final Result postingResult = executor.execute(testPosting);
        Assert.assertEquals(Result.EMPTY, postingResult);
    }

    @Test
    public void readingAfterPostingOutputsPostedMessage() {
        final Instant timeOfPosting = Instant.parse("2015-04-10T23:45:00Z");
        final TestClock clock = TestClock.withCurrentInstant(timeOfPosting);
        final CommandExecutor executor = new CommandExecutor(clock);

        executor.execute(testPosting);
        clock.advance(Duration.ofMinutes(3));
        final Result readingResult = executor.execute(new Reading(testPosting.userName));

        Assert.assertEquals(Result.withMessages(new Message(testPosting.userName, testPosting.message, timeOfPosting)), readingResult);
    }
}

