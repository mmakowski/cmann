package com.mmakowski.cmann.assembly;

import com.google.common.base.Objects;

import java.time.Instant;

public final class Message {
    public final String userName;
    public final String message;
    public final Instant timestamp;

    public Message(final String userName, final String message, final Instant timestamp) {
        this.userName = userName;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Message message1 = (Message) o;
        return Objects.equal(userName, message1.userName) &&
                Objects.equal(message, message1.message) &&
                Objects.equal(timestamp, message1.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName, message, timestamp);
    }
}
