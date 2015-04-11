package com.mmakowski.cmann.model;

import com.google.common.base.Objects;

public final class Reading implements Command {
    public final String userName;

    public Reading(final String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Reading{" +
                "userName='" + userName + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Reading reading = (Reading) o;
        return Objects.equal(userName, reading.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }
}
