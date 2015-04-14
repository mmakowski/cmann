package com.mmakowski.cmann.exec;

import com.google.common.collect.ImmutableList;
import com.mmakowski.cmann.model.*;
import com.mmakowski.util.TestClock;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;

public final class CmAnnCommandExecutorTest {
    @Test
    public void postingInsertsMessageIntoTheStoreAndProducesNoOutput() {
        final Store store = Mockito.mock(Store.class);
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(store, TestClock.withCurrentInstant(timeOfPosting));

        final Result postingResult = executor.execute(testPosting);

        Mockito.verify(store, Mockito.times(1)).insertMessage(testMessage);
        Mockito.verifyNoMoreInteractions(store);
        Assert.assertEquals(Results.EMPTY, postingResult);
    }

    @Test
    public void readingProducesMessagesBySuppliedUserProvidedByTheStore() {
        final Store store = Mockito.mock(Store.class);
        final List<Message> messagesByAlice = ImmutableList.of(
                new Message(alice, "message 1", timeOfPosting.plusSeconds(1)),
                new Message(alice, "message 2", timeOfPosting)
        );
        Mockito.when(store.messagesByUser(alice)).thenReturn(messagesByAlice);
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(store, new TestClock());

        Assert.assertEquals(ReadingResult.withMessages(messagesByAlice), executor.execute(new Reading(alice)));
    }

    @Test
    public void followingInsertsSubscriptionIntoTheStoreAndProducesNoOutput() {
        final Store store = Mockito.mock(Store.class);
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(store, new TestClock());

        final Result followingResult = executor.execute(new Following("Charlie", alice));

        Mockito.verify(store, Mockito.times(1)).insertSubsription("Charlie", alice);
        Mockito.verifyNoMoreInteractions(store);
        Assert.assertEquals(Results.EMPTY, followingResult);
    }

    @Test
    public void wallProducesMessagesFromSuppliedUsersWallProvidedByTheStore() {
        final Store store = Mockito.mock(Store.class);
        final List<Message> messagesFromAlicesWall = ImmutableList.of(
                new Message(alice, "message 1", timeOfPosting.plusSeconds(1)),
                new Message("Bob", "message 2", timeOfPosting)
        );
        Mockito.when(store.wallMessages(alice)).thenReturn(messagesFromAlicesWall);
        final CmAnnCommandExecutor executor = new CmAnnCommandExecutor(store, new TestClock());

        Assert.assertEquals(WallResult.withMessages(messagesFromAlicesWall), executor.execute(new Wall(alice)));
    }

    private static final String alice = "Alice";
    private static final Posting testPosting = new Posting(alice, "I love the weather today");
    private static final Instant timeOfPosting = Instant.parse("2015-04-10T23:45:00Z");
    private static final Message testMessage = new Message(testPosting.userName, testPosting.message, timeOfPosting);
}
