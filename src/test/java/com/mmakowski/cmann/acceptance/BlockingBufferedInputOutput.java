package com.mmakowski.cmann.acceptance;

import com.mmakowski.cmann.text.InputReader;
import com.mmakowski.cmann.text.OutputWriter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

final class BlockingBufferedInputOutput {
    public final OutputWriter writer;
    public final InputReader reader;

    public BlockingBufferedInputOutput() {
        final BlockingQueue<String> buffer = new LinkedBlockingQueue<>();
        writer = new BlockingQueueWriter(buffer);
        reader = new BlockingQueueReader(buffer);
    }
}
