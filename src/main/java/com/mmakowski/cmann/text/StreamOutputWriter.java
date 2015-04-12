package com.mmakowski.cmann.text;

import java.io.OutputStream;
import java.io.PrintWriter;

public final class StreamOutputWriter implements OutputWriter {
    private final PrintWriter out;

    public StreamOutputWriter(final OutputStream out) {
        this.out = new PrintWriter(out);
    }

    @Override
    public void write(final String str) {
        out.write(str);
        out.flush();
    }

    @Override
    public void writeLine(final String line) {
        write(line + "\n");
    }
}
