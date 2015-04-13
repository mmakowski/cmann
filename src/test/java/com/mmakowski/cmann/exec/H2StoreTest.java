package com.mmakowski.cmann.exec;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mmakowski.cmann.model.Message;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

public final class H2StoreTest {
    @Test
    public void readingAfterInsertionReturnsPostedMessage() {
        try (H2Store store = new H2Store()) {
            store.insertMessage(testMessage);

            Assert.assertEquals(ImmutableList.of(testMessage), store.messagesByUser(testMessage.userName));
        }
    }

    @Test
    public void readingReturnsAllMessagesOfSpecifiedUserInReverseOrderOfPostingTime() {
        try (H2Store store = new H2Store()) {
            for (final Message message : alicesAndBobsMessages) store.insertMessage(message);
            Assert.assertEquals(alicesMessages.reverse(), store.messagesByUser(alice));
        }
    }

    @Test
    public void wallReturnsOwnMessagesInReverseOrderOfPosting() {
        try (H2Store store = new H2Store()) {
            for (final Message message : alicesAndBobsMessages) store.insertMessage(message);
            Assert.assertEquals(alicesMessages.reverse(), store.wallMessages(alice));
        }
    }

    @Test
    public void wallReturnsAllSubscribedMessagesInReverseOrderOfPosting() {
        try (H2Store store = new H2Store()) {
            for (final Message message : alicesAndBobsMessages) store.insertMessage(message);
            store.insertMessage(new Message("SomeoneElse", "message that should not appear in the wall", timeOfInitialPosting));
            store.insertSubsription(alice, bob);

            final List<Message> expectedWallMessages = ImmutableList.of(
                    alicesMessages.get(2),
                    bobsMessages.get(0),
                    alicesMessages.get(1),
                    alicesMessages.get(0)
            );
            Assert.assertEquals(expectedWallMessages, store.wallMessages(alice));
        }
    }

    private static final String alice = "Alice";
    private static final String bob = "Bob";
    private static final Instant timeOfInitialPosting = Instant.parse("2015-04-10T23:45:00Z");
    private static final ImmutableList<Message> alicesMessages = ImmutableList.of(
            new Message(alice, "I love the weather today", timeOfInitialPosting),
            new Message(alice, "And today as well", timeOfInitialPosting.plusSeconds(1)),
            new Message(alice, "What was the score?", timeOfInitialPosting.plusSeconds(2))
    );
    private static final ImmutableList<Message> bobsMessages = ImmutableList.of(
            new Message(bob, "Damn! We lost!", timeOfInitialPosting.plusMillis(1999))
    );
    final Iterable<Message> alicesAndBobsMessages = Iterables.concat(alicesMessages, bobsMessages);
    private static final Message testMessage = alicesMessages.get(0);
}
