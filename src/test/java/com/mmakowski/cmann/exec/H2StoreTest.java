package com.mmakowski.cmann.exec;

import com.google.common.collect.ImmutableList;
import com.mmakowski.cmann.model.Message;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

public final class H2StoreTest {
    @Test
    public void readingAfterPostingOutputsPostedMessage() {
        final H2Store store = new H2Store();

        store.insertMessage(testMessage);

        Assert.assertEquals(ImmutableList.of(testMessage), store.messagesByUser(testMessage.userName));
    }

    private static final String alice = "Alice";
    private static final String bob = "Bob";
    private static final Instant timeOfPosting = Instant.parse("2015-04-10T23:45:00Z");
    private static final List<Message> alicesMessages = ImmutableList.of(
            new Message(alice, "I love the weather today", timeOfPosting),
            new Message(alice, "And today as well", timeOfPosting),
            new Message(alice, "What was the score?", timeOfPosting)
    );
    private static final List<Message> bobsMessages = ImmutableList.of(
            new Message(bob, "Damn! We lost!", timeOfPosting)
    );
    private static final Message testMessage = alicesMessages.get(0);
}
