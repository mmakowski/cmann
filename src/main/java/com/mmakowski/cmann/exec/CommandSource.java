package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.Command;

import java.util.Optional;

public interface CommandSource {
    Optional<Command> blockingGetCommand() throws InterruptedException;
}
