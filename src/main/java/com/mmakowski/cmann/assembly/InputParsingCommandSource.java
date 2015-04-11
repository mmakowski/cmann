package com.mmakowski.cmann.assembly;

public final class InputParsingCommandSource implements CommandSource {
    private final InputReader reader;
    private final CommandParser parser;

    public InputParsingCommandSource(final InputReader reader, final CommandParser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    @Override
    public Command nextCommand() throws InterruptedException {
        return parser.parse(reader.blockingReadLine());
    }
}
