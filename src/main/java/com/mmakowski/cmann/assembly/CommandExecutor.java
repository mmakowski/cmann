package com.mmakowski.cmann.assembly;

import com.mmakowski.util.Clock;

import java.util.ArrayList;
import java.util.List;

public final class CommandExecutor {
    private final Clock clock;
    private final List<Message> messages = new ArrayList<>();

    public CommandExecutor(final Clock clock) {
        this.clock = clock;
    }

    public Result execute(final Posting posting) {
        messages.add(new Message(posting.userName, posting.message, clock.currentInstant()));
        return Result.EMPTY;
    }

    public Result execute(Reading reading) {
        return Result.withMessages(messages);
    }
}
