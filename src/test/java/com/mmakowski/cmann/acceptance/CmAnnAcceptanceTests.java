package com.mmakowski.cmann.acceptance;

import com.mmakowski.cmann.assembly.CmAnnAssembly;
import com.mmakowski.cmann.testcategories.AcceptanceTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;

@Category(AcceptanceTest.class)
public final class CmAnnAcceptanceTests {
    @Test
    public void postedMessagesCanBeRead() {
        try (Fixture test = new Fixture()) {
            test.assertOutput(">");
            test.input("Alice -> I love the weather today");
            test.assertOutput(">");
            test.advanceClock(3, MINUTES);
            test.input("Bob -> Damn! We lost!");
            test.assertOutput(">");
            test.advanceClock(1, MINUTES);
            test.input("Bob -> Good game though.");
            test.assertOutput(">");
            test.advanceClock(1, MINUTES);
            test.input("Alice");
            test.assertOutput("I love the weather today (5 minutes ago)",
                              ">");
            test.input("Bob");
            test.assertOutput("Good game though. (1 minute ago)",
                              "Damn! We lost! (2 minutes ago)",
                              ">");
        }
    }

    private static final class Fixture implements AutoCloseable {
        private final TestClock clock = new TestClock();
        private final BlockingBufferedInputOutput in = new BlockingBufferedInputOutput();
        private final BlockingBufferedInputOutput out = new BlockingBufferedInputOutput();
        private final ExecutorService asyncExecutor = Executors.newSingleThreadExecutor();
        private final TimeoutBasedAvailableLinesReader linesReader = TimeoutBasedAvailableLinesReader.withReadTimeoutMs(50);

        public Fixture() {
            final CmAnnAssembly assembly = new CmAnnAssembly(in.reader, out.writer, clock);
            asyncExecutor.execute(assembly);
        }

        public void input(final String line) {
            in.writer.writeLine(line);
        }

        public void assertOutput(final String... expectedLines) {
            final List<String> actualLines = linesReader.readAvailableLines(out.reader);
            Assert.assertEquals(Arrays.asList(expectedLines), actualLines);
        }

        public void advanceClock(final int time, final TimeUnit timeUnit) {
            clock.advance(time, timeUnit);
        }

        @Override
        public void close() {
            linesReader.close();
            ExecutorServices.shutdownSynchronously(asyncExecutor);
        }
    }
}

