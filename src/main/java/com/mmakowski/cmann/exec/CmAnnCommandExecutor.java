package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.*;
import com.mmakowski.util.Clock;

public final class CmAnnCommandExecutor implements CommandExecutor {
    private final Store store;
    private final Clock clock;

    public CmAnnCommandExecutor(final Store store, final Clock clock) {
        this.store = store;
        this.clock = clock;
    }

    public Result execute(final Command command) {
        if (command instanceof Posting) return execute((Posting) command);
        else if (command instanceof Reading) return execute((Reading) command);
        else if (command instanceof Following) return execute((Following) command);
        else if (command instanceof Wall) return execute((Wall) command);
        else throw new UnsupportedOperationException("Unsupported command: " + command);
    }

    private Result execute(final Posting posting) {
        store.insertMessage(new Message(posting.userName, posting.message, clock.currentInstant()));
        return Result.EMPTY;
    }

    private Result execute(final Reading reading) {
        return Result.withMessages(messagesBy(reading.userName));
    }

    private Result execute(final Following following) {
        return Result.EMPTY;
    }

    private Result execute(final Wall wall) {
        return Result.withMessages(messagesBy(wall.userName));
    }

    private Iterable<Message> messagesBy(final String userName) {
        return store.messagesByUser(userName);
    }
}
