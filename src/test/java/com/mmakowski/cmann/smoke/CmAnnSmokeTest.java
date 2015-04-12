package com.mmakowski.cmann.smoke;

import com.mmakowski.cmann.CmAnnApp;
import com.mmakowski.cmann.testcategories.SmokeTest;
import com.mmakowski.util.TimingOut;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;

@Category(SmokeTest.class)
public final class CmAnnSmokeTest {
    @Test
    public void reactsToCommandsFromCommandLine() throws IOException, InterruptedException {
        final Duration lineReadTimeout = Duration.ofMillis(100);
        final Process process = startApp();
        try {
            final PrintWriter inputWriter = new PrintWriter(process.getOutputStream());
            final BufferedReader outputReader = new BufferedReader(new InputStreamReader((process.getInputStream())));
            inputWriter.println("Alice -> smoke test");
            inputWriter.println("Alice");
            eventually(() -> {
                final String outputLine = TimingOut.execute((TimingOut.Block<String>) outputReader::readLine, lineReadTimeout);
                Assert.assertEquals("smoke test", outputLine);
            });
        } finally {
            process.destroyForcibly();
        }
    }

    private static Process startApp() throws IOException {
        final ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("java", "-cp", currentClasspath(), CmAnnApp.class.getName());
        return processBuilder.start();
    }

    private static String currentClasspath() {
        return System.getProperty("java.class.path");
    }

    private static final Duration TIMEOUT = Duration.ofSeconds(1);
    private static final Duration RETRY_DELAY = Duration.ofMillis(100);

    private static void eventually(final Block block) throws InterruptedException {
        boolean succeeded = false;
        Throwable lastFailure = null;
        final Instant startTime = Instant.now();
        while (!succeeded && Instant.now().isBefore(startTime.plus(TIMEOUT))) {
            try {
                block.execute();
                succeeded = true;
            } catch (final Throwable t) {
                lastFailure = t;
                Thread.sleep(RETRY_DELAY.toMillis());
            }
        }
        if (!succeeded) {
            throw new AssertionError("timed out after " + Duration.between(startTime, Instant.now()).toMillis() + "ms, see cause for last encountered failure",
                                     lastFailure);
        }
    }

    private interface Block {
        void execute() throws Exception;
    }
}


