package com.mmakowski.cmann.text;

import com.mmakowski.cmann.model.Command;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Optional;

public final class InputParsingCommandSourceTest {
    @Test
    public void parsesNextInputLine() throws InterruptedException {
        final String inputLine1 = "line 1";
        final String inputLine2 = "line 2";
        final Command command1 = Mockito.mock(Command.class, "command 1");
        final Command command2 = Mockito.mock(Command.class, "command 2");

        final OutputWriter writer = Mockito.mock(OutputWriter.class);

        final InputReader reader = Mockito.mock(InputReader.class);
        Mockito.when(reader.blockingReadLine()).thenReturn(Optional.of(inputLine1)).thenReturn(Optional.of(inputLine2));

        final CommandParser parser = Mockito.mock(CommandParser.class);
        Mockito.when(parser.parse(inputLine1)).thenReturn(command1);
        Mockito.when(parser.parse(inputLine2)).thenReturn(command2);

        final InputParsingCommandSource source = new InputParsingCommandSource(writer, reader, parser);

        Assert.assertEquals(Optional.of(command1), source.blockingGetCommand());
        Assert.assertEquals(Optional.of(command2), source.blockingGetCommand());
    }

    @Test
    public void outputsPromptBeforeProcessingInput() throws InterruptedException {
        final OutputWriter writer = Mockito.mock(OutputWriter.class);
        final InputReader reader = Mockito.mock(InputReader.class);
        Mockito.when(reader.blockingReadLine()).thenReturn(Optional.empty());
        final CommandParser parser = Mockito.mock(CommandParser.class);

        final InputParsingCommandSource source = new InputParsingCommandSource(writer, reader, parser);

        source.blockingGetCommand();

        final InOrder inOrder = Mockito.inOrder(writer, reader);
        inOrder.verify(writer, Mockito.times(1)).write("> ");
        inOrder.verify(reader, Mockito.times(1)).blockingReadLine();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void returnsEmptyWhenReaderReturnsEmpty() throws InterruptedException {
        final OutputWriter writer = Mockito.mock(OutputWriter.class);
        final InputReader reader = Mockito.mock(InputReader.class);
        Mockito.when(reader.blockingReadLine()).thenReturn(Optional.empty());
        final CommandParser parser = Mockito.mock(CommandParser.class);

        final InputParsingCommandSource source = new InputParsingCommandSource(writer, reader, parser);

        Assert.assertEquals(Optional.empty(), source.blockingGetCommand());
    }
}

