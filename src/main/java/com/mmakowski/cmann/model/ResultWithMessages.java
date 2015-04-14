package com.mmakowski.cmann.model;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

import java.util.List;

abstract class ResultWithMessages implements Result {
    private final ImmutableList<Message> messages;

    protected ResultWithMessages(final ImmutableList<Message> messages) {
        this.messages = messages;
    }

    public List<Message> messages() {
        return messages;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "messages=" + messages +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ResultWithMessages result = (ResultWithMessages) o;
        return Objects.equal(messages, result.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messages);
    }
}
