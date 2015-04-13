package com.mmakowski.cmann.model;

import com.google.common.base.Objects;

public final class Wall implements Command {
    public final String userName;

    public Wall(final String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "userName='" + userName + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Wall wall = (Wall) o;
        return Objects.equal(userName, wall.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }
}
