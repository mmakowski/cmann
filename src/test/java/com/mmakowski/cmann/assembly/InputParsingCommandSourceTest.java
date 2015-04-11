package com.mmakowski.cmann.assembly;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class InputParsingCommandSourceTest {
    @Test
    public void parsesNextInputLine() throws InterruptedException {
        final String inputLine1 = "line 1";
        final String inputLine2 = "line 2";
        final Command command1 = Mockito.mock(Command.class, "command 1");
        final Command command2 = Mockito.mock(Command.class, "command 2");

        final InputReader reader = Mockito.mock(InputReader.class);
        Mockito.when(reader.blockingReadLine()).thenReturn(inputLine1).thenReturn(inputLine2);

        final CommandParser parser = Mockito.mock(CommandParser.class);
        Mockito.when(parser.parse(inputLine1)).thenReturn(command1);
        Mockito.when(parser.parse(inputLine2)).thenReturn(command2);

        final InputParsingCommandSource source = new InputParsingCommandSource(reader, parser);

        Assert.assertEquals(command1, source.nextCommand());
        Assert.assertEquals(command2, source.nextCommand());
    }
}

