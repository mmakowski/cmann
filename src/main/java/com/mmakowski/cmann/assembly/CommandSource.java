package com.mmakowski.cmann.assembly;

import com.mmakowski.cmann.model.Command;

public interface CommandSource {
    Command blockingGetCommand() throws InterruptedException;
}
