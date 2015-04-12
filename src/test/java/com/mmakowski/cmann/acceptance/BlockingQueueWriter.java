package com.mmakowski.cmann.acceptance;

import com.mmakowski.cmann.text.OutputWriter;

import java.util.concurrent.BlockingQueue;

final class BlockingQueueWriter implements OutputWriter {
    private final BlockingQueue<String> queue;

    public BlockingQueueWriter(final BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void write(final String str) {
        queue.offer(str);
    }

    @Override
    public void writeLine(final String line) {
        queue.offer(line + "\n");
    }
}
