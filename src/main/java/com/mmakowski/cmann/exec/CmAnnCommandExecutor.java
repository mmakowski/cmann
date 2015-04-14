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
        return Results.EMPTY;
    }

    private Result execute(final Reading reading) {
        return ReadingResult.withMessages(store.messagesByUser(reading.userName));
    }

    private Result execute(final Following following) {
        store.insertSubsription(following.follower, following.followee);
        return Results.EMPTY;
    }

    private Result execute(final Wall wall) {
        return ReadingResult.withMessages(store.wallMessages(wall.userName));
    }
}
