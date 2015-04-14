package com.mmakowski.cmann.text;

import java.util.Optional;

public interface InputReader {
    Optional<String> blockingReadLine() throws InterruptedException;
}
