package com.mmakowski.cmann.assembly;

public class Posting implements Command {
    public final String userName;
    public final String message;

    public Posting(final String userName, final String message) {
        this.userName = userName;
        this.message = message;
    }
}
