package com.mmakowski.cmann.text;

import com.google.common.base.Charsets;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class StreamOutputWriterTest {
    @Test
    public void writesStringToTheOutput() throws IOException {
        assertOutputWritten("some text", writer -> writer.write("some text"));
    }

    @Test
    public void writesLineToTheOutput() throws IOException {
        assertOutputWritten("some text\n", writer -> writer.writeLine("some text"));
    }

    private static void assertOutputWritten(final String expectedOutput, final WriterInteraction body) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            final StreamOutputWriter writer = new StreamOutputWriter(out);

            body.execute(writer);

            final String actualOutput = new String(out.toByteArray(), Charsets.UTF_8);
            Assert.assertEquals(expectedOutput, actualOutput);
        }
    }

    private interface WriterInteraction {
        void execute(StreamOutputWriter writer);
    }
}
