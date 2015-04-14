package com.mmakowski.cmann.model;

import com.google.common.collect.ImmutableList;

public final class WallResult extends ResultWithMessages {
    public static WallResult withMessages(final Message... messages) {
        return new WallResult(ImmutableList.copyOf(messages));
    }

    public static WallResult withMessages(final Iterable<Message> messages) {
        return new WallResult(ImmutableList.copyOf(messages));
    }

    private WallResult(final ImmutableList<Message> messages) {
        super(messages);
    }
}
