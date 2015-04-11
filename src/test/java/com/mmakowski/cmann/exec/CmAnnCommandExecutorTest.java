package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.Message;
import com.mmakowski.cmann.model.Posting;
import com.mmakowski.cmann.model.Reading;
import com.mmakowski.cmann.model.Result;
import com.mmakowski.util.TestClock;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

public final class CmAnnCommandExecutorTest {
    private static final Posting testPosting = new Posting("Alice", "I love the weather today");

    @Test
    public void postingResultsInNoOutput() {
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(new TestClock());
        final Result postingResult = executor.execute(testPosting);
        Assert.assertEquals(Result.EMPTY, postingResult);
    }

    @Test
    public void readingAfterPostingOutputsPostedMessage() {
        final Instant timeOfPosting = Instant.parse("2015-04-10T23:45:00Z");
        final TestClock clock = TestClock.withCurrentInstant(timeOfPosting);
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(clock);

        executor.execute(testPosting);
        clock.advance(Duration.ofMinutes(3));
        final Result readingResult = executor.execute(new Reading(testPosting.userName));

        Assert.assertEquals(Result.withMessages(new Message(testPosting.userName, testPosting.message, timeOfPosting)), readingResult);
    }
}
