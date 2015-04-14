package com.mmakowski.cmann.text;

import com.google.common.base.Charsets;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

public class StreamInputReaderTest {
    @Test
    public void returnsLineFromTheInputStream() throws InterruptedException, IOException {
        final String inputText = "an input line\n";
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(inputText.getBytes(Charsets.UTF_8))) {
            final StreamInputReader reader = new StreamInputReader(inputStream);
            Assert.assertEquals(Optional.of(stripEndOfLine(inputText)), reader.blockingReadLine());
        }
    }

    @Test
    public void returnsSubsequentLinesFromTheInputStream() throws InterruptedException, IOException {
        final String secondLine = "secondLine\n";
        final String inputText = "first line\n" +
                                 secondLine;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(inputText.getBytes(Charsets.UTF_8))) {
            final StreamInputReader reader = new StreamInputReader(inputStream);
            reader.blockingReadLine();
            Assert.assertEquals(Optional.of(stripEndOfLine(secondLine)), reader.blockingReadLine());
        }
    }

    @Test
    public void returnsEmptyWhenEndOfStreamIsReached() throws InterruptedException, IOException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[] {})) {
            final StreamInputReader reader = new StreamInputReader(inputStream);
            Assert.assertEquals(Optional.empty(), reader.blockingReadLine());
        }
    }

    private static String stripEndOfLine(final String line) {
        return line.replace("\n", "");
    }
}
