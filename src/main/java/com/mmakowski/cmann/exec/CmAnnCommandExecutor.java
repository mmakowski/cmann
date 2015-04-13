package com.mmakowski.cmann.exec;

import com.google.common.collect.ImmutableList;
import com.mmakowski.cmann.model.*;
import com.mmakowski.util.Clock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CmAnnCommandExecutor implements CommandExecutor {
    private final Clock clock;
    private final Map<String, List<Message>> messagesPerUser = new HashMap<>();

    public CmAnnCommandExecutor(final Clock clock) {
        this.clock = clock;
    }

    public Result execute(final Command command) {
        if (command instanceof Posting) return execute((Posting) command);
        else if (command instanceof Reading) return execute((Reading) command);
        else if (command instanceof Following) return execute((Following) command);
        else throw new UnsupportedOperationException("Unsupported command: " + command);
    }

    private Result execute(final Posting posting) {
        final ImmutableList.Builder<Message> updatedMessages = ImmutableList.builder();
        updatedMessages.add(new Message(posting.userName, posting.message, clock.currentInstant()));
        updatedMessages.addAll(messagesBy(posting.userName));
        messagesPerUser.put(posting.userName, updatedMessages.build());
        return Result.EMPTY;
    }

    private Result execute(final Reading reading) {
        return Result.withMessages(messagesBy(reading.userName));
    }

    private Result execute(final Following following) {
        return Result.EMPTY;
    }

    private Iterable<Message> messagesBy(final String userName) {
        return messagesPerUser.getOrDefault(userName, ImmutableList.of());
    }
}
