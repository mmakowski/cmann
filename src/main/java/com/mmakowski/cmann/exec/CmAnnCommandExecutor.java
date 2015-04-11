package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.*;
import com.mmakowski.util.Clock;

import java.util.ArrayList;
import java.util.List;

public final class CmAnnCommandExecutor implements CommandExecutor {
    private final Clock clock;
    private final List<Message> messages = new ArrayList<>();

    public CmAnnCommandExecutor(final Clock clock) {
        this.clock = clock;
    }

    public Result execute(final Command command) {
        if (command instanceof Posting) return execute((Posting) command);
        else if (command instanceof Reading) return execute((Reading) command);
        else throw new UnsupportedOperationException("Unsupported command: " + command);
    }

    private Result execute(final Posting posting) {
        messages.add(new Message(posting.userName, posting.message, clock.currentInstant()));
        return Result.EMPTY;
    }

    private Result execute(final Reading reading) {
        return Result.withMessages(messages);
    }
}
