package com.mmakowski.cmann.assembly;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

public final class Result {
    public static final Result EMPTY = new Result(ImmutableList.<Message>of());

    public final ImmutableList<Message> messages;

    public static Result withMessages(final Message... messages) {
        return new Result(ImmutableList.copyOf(messages));
    }

    public static Result withMessages(final Iterable<Message> messages) {
        return new Result(ImmutableList.copyOf(messages));
    }

    private Result(final ImmutableList<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Result{" +
                "messages=" + messages +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Result result = (Result) o;
        return Objects.equal(messages, result.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messages);
    }
}
