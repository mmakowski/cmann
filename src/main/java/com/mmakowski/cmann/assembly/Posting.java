package com.mmakowski.cmann.assembly;

import com.google.common.base.Objects;

public class Posting implements Command {
    public final String userName;
    public final String message;

    public Posting(final String userName, final String message) {
        this.userName = userName;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Posting{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Posting posting = (Posting) o;
        return Objects.equal(userName, posting.userName) &&
                Objects.equal(message, posting.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName, message);
    }
}
