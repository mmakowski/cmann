package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.Command;

public interface CommandSource {
    Command blockingGetCommand() throws InterruptedException;
}
