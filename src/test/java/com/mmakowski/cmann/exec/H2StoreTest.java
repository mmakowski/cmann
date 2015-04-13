package com.mmakowski.cmann.exec;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mmakowski.cmann.model.Message;
import com.mmakowski.cmann.model.Posting;
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
    public void wallOutputsOwnMessagesInReverseOrderOfPosting() {
        try (H2Store store = new H2Store()) {
            for (final Message message : alicesAndBobsMessages) store.insertMessage(message);
            Assert.assertEquals(alicesMessages.reverse(), store.wallMessages(alice));
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
            new Message(bob, "Damn! We lost!", timeOfInitialPosting.plusSeconds(2))
    );
    final Iterable<Message> alicesAndBobsMessages = Iterables.concat(alicesMessages, bobsMessages);
    private static final Message testMessage = alicesMessages.get(0);
}
