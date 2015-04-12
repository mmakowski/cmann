package com.mmakowski.cmann.exec;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mmakowski.cmann.model.Message;
import com.mmakowski.cmann.model.Posting;
import com.mmakowski.cmann.model.Reading;
import com.mmakowski.cmann.model.Result;
import com.mmakowski.util.TestClock;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public final class CmAnnCommandExecutorTest {
    @Test
    public void postingResultsInNoOutput() {
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(new TestClock());
        final Result postingResult = executor.execute(testPosting);
        Assert.assertEquals(Result.EMPTY, postingResult);
    }

    @Test
    public void readingAfterPostingOutputsPostedMessage() {
        final TestClock clock = TestClock.withCurrentInstant(timeOfPosting);
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(clock);

        executor.execute(testPosting);
        clock.advance(Duration.ofMinutes(3));
        final Result readingResult = executor.execute(new Reading(testPosting.userName));

        Assert.assertEquals(Result.withMessages(toMessage.apply(testPosting)), readingResult);
    }

    @Test
    public void readingOutputsAllMessagesOfSpecifiedUserInInverseOrderOfPosting() {
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(TestClock.withCurrentInstant(timeOfPosting));

        for (final Posting posting : Iterables.concat(alicesPostings, bobsPostings)) executor.execute(posting);

        final Result readingResult = executor.execute(new Reading(alice));

        final Iterable<Message> reversedAlicesMessages = ImmutableList.copyOf(Iterables.transform(alicesPostings, toMessage)).reverse();
        final Result expectedResult = Result.withMessages(reversedAlicesMessages);
        Assert.assertEquals(expectedResult, readingResult);
    }

    private static final String alice = "Alice";
    private static final List<Posting> alicesPostings = ImmutableList.of(
            new Posting(alice, "I love the weather today"),
            new Posting(alice, "And today as well"),
            new Posting(alice, "What was the score?")
    );
    private static final List<Posting> bobsPostings = ImmutableList.of(
            new Posting("Bob", "Damn! We lost!")
    );
    private static final Posting testPosting = alicesPostings.get(0);
    private static final Instant timeOfPosting = Instant.parse("2015-04-10T23:45:00Z");
    private static final Function<Posting, Message> toMessage = posting -> new Message(posting.userName, posting.message, timeOfPosting);
}
