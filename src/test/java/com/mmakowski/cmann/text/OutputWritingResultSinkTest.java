package com.mmakowski.cmann.text;

import com.mmakowski.cmann.model.Message;
import com.mmakowski.cmann.model.ReadingResult;
import com.mmakowski.cmann.model.WallResult;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;

public final class OutputWritingResultSinkTest {
    @Test
    public void writesToOutputReadMessagesFormattedWithReadingFormat() {
        final MessageFormat readingFormat = Mockito.mock(MessageFormat.class);
        final MessageFormat wallFormat = Mockito.mock(MessageFormat.class);
        expectUseOfFormat(readingFormat);

        final OutputWriter out = Mockito.mock(OutputWriter.class);

        final OutputWritingResultSink sink = new OutputWritingResultSink(out, readingFormat, wallFormat);
        final ReadingResult result = ReadingResult.withMessages(message1, message2);

        sink.receive(result);

        Mockito.verify(out, Mockito.times(1)).writeLine(formattedMessage1);
        Mockito.verify(out, Mockito.times(1)).writeLine(formattedMessage2);
        Mockito.verifyNoMoreInteractions(out, wallFormat);
    }

    @Test
    public void writesToOutputWallMessagesFormattedWithWallFormat() {
        final MessageFormat readingFormat = Mockito.mock(MessageFormat.class);
        final MessageFormat wallFormat = Mockito.mock(MessageFormat.class);
        expectUseOfFormat(wallFormat);

        final OutputWriter out = Mockito.mock(OutputWriter.class);

        final OutputWritingResultSink sink = new OutputWritingResultSink(out, readingFormat, wallFormat);
        final WallResult result = WallResult.withMessages(message1, message2);

        sink.receive(result);

        Mockito.verify(out, Mockito.times(1)).writeLine(formattedMessage1);
        Mockito.verify(out, Mockito.times(1)).writeLine(formattedMessage2);
        Mockito.verifyNoMoreInteractions(out, readingFormat);
    }

    private static void expectUseOfFormat(final MessageFormat mockFormat) {
        Mockito.when(mockFormat.apply(message1)).thenReturn(formattedMessage1);
        Mockito.when(mockFormat.apply(message2)).thenReturn(formattedMessage2);
    }

    private static final Message message1 = new Message("Alice", "First message", Instant.parse("2015-04-12T09:15:30Z"));
    private static final Message message2 = new Message("Bob", "Second message", Instant.parse("2015-04-12T10:00:30Z"));
    private static final String formattedMessage1 = "Alice - First message (2 minutes)";
    private static final String formattedMessage2 = "Bob - Second message (1 minute)";
}
