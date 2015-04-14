package com.mmakowski.cmann.model;

import com.google.common.collect.ImmutableList;

public final class ReadingResult extends ResultWithMessages {
    public static ReadingResult withMessages(final Message... messages) {
        return new ReadingResult(ImmutableList.copyOf(messages));
    }

    public static ReadingResult withMessages(final Iterable<Message> messages) {
        return new ReadingResult(ImmutableList.copyOf(messages));
    }

    private ReadingResult(final ImmutableList<Message> messages) {
        super(messages);
    }
}
