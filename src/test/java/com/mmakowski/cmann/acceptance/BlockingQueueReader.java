package com.mmakowski.cmann.acceptance;

import com.mmakowski.cmann.text.InputReader;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

final class BlockingQueueReader implements InputReader {
    private final BlockingQueue<String> queue;

    public BlockingQueueReader(final BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public Optional<String> blockingReadLine() throws InterruptedException {
        return Optional.of(queue.take().replace("\n", ""));
    }
}
