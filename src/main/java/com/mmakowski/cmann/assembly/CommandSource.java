package com.mmakowski.cmann.assembly;

public interface CommandSource {
    Command nextCommand() throws InterruptedException;
}
