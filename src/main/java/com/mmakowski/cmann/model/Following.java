package com.mmakowski.cmann.model;

import com.google.common.base.Objects;

public final class Following implements Command {
    public final String follower;
    public final String followee;

    public Following(final String follower, final String followee) {
        this.follower = follower;
        this.followee = followee;
    }

    @Override
    public String toString() {
        return "Following{" +
                "follower='" + follower + '\'' +
                ", followee='" + followee + '\'' +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Following following = (Following) o;
        return Objects.equal(follower, following.follower) &&
                Objects.equal(followee, following.followee);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(follower, followee);
    }
}
