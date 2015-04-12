package com.mmakowski.cmann.text;

import com.mmakowski.cmann.model.Message;
import com.mmakowski.cmann.model.Result;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;

public final class OutputWritingResultSinkTest {
    @Test
    public void writesAllFormattedMessagesToOutput() {
        final Message message1 = new Message("Alice", "First message", Instant.parse("2015-04-12T09:15:30Z"));
        final Message message2 = new Message("Bob", "Second message", Instant.parse("2015-04-12T10:00:30Z"));
        final String formattedMessage1 = "Alice - First message (2 minutes)";
        final String formattedMessage2 = "Bob - Second message (1 minute)";

        final MessageFormat format = Mockito.mock(MessageFormat.class);
        Mockito.when(format.apply(message1)).thenReturn(formattedMessage1);
        Mockito.when(format.apply(message2)).thenReturn(formattedMessage2);

        final OutputWriter out = Mockito.mock(OutputWriter.class);

        final OutputWritingResultSink sink = new OutputWritingResultSink(out, format);
        final Result result = Result.withMessages(message1, message2);

        sink.receive(result);

        Mockito.verify(out, Mockito.times(1)).writeLine(formattedMessage1);
        Mockito.verify(out, Mockito.times(1)).writeLine(formattedMessage2);
        Mockito.verifyNoMoreInteractions(out);
    }
}
