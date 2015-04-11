package com.mmakowski.cmann.text;

import com.mmakowski.cmann.exec.CommandSource;
import com.mmakowski.cmann.model.Command;

public final class InputParsingCommandSource implements CommandSource {
    private final InputReader reader;
    private final CommandParser parser;

    public InputParsingCommandSource(final InputReader reader, final CommandParser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    @Override
    public Command blockingGetCommand() throws InterruptedException {
        return parser.parse(reader.blockingReadLine());
    }
}
