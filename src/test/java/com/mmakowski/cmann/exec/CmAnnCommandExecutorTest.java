package com.mmakowski.cmann.exec;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mmakowski.cmann.model.*;
import com.mmakowski.util.TestClock;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;

public final class CmAnnCommandExecutorTest {
    @Test
    public void postingResultsInNoOutput() {
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(Mockito.mock(Store.class), new TestClock());
        final Result postingResult = executor.execute(testPosting);
        Assert.assertEquals(Result.EMPTY, postingResult);
    }

    @Test
    public void followingResultsInNoOutput() {
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(Mockito.mock(Store.class), new TestClock());
        final Result followingResult = executor.execute(new Following("Charlie", "Alice"));
        Assert.assertEquals(Result.EMPTY, followingResult);
    }

    @Ignore
    @Test
    public void wallOutputsAllSubscribedMessagesInReverseOrderOfPosting() {
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(Mockito.mock(Store.class), TestClock.withCurrentInstant(timeOfPosting));

        final Iterable<Posting> alicesAndBobsPostings = Iterables.concat(alicesPostings, bobsPostings);
        for (final Posting posting : alicesAndBobsPostings) executor.execute(posting);
        executor.execute(new Posting("SomeoneElse", "message that should not appear in the wall"));

        executor.execute(new Following(alice, bob));
        final Result wallResult = executor.execute(new Wall(alice));

        final Iterable<Message> alicesAndBobsMessagesReversed = ImmutableList.copyOf(Iterables.transform(alicesAndBobsPostings, toMessage)).reverse();
        final Result expectedResult = Result.withMessages(alicesAndBobsMessagesReversed);
        Assert.assertEquals(expectedResult, wallResult);
    }

    private static final String alice = "Alice";
    private static final String bob = "Bob";
    private static final List<Posting> alicesPostings = ImmutableList.of(
            new Posting(alice, "I love the weather today"),
            new Posting(alice, "And today as well"),
            new Posting(alice, "What was the score?")
    );
    private static final List<Posting> bobsPostings = ImmutableList.of(
            new Posting(bob, "Damn! We lost!")
    );
    private static final Posting testPosting = alicesPostings.get(0);
    private static final Instant timeOfPosting = Instant.parse("2015-04-10T23:45:00Z");
    private static final Function<Posting, Message> toMessage = posting -> new Message(posting.userName, posting.message, timeOfPosting);
}
