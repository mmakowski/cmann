package com.mmakowski.cmann.acceptance;

import com.google.common.collect.ImmutableList;
import com.mmakowski.cmann.assembly.CmAnnAssembly;
import com.mmakowski.cmann.testcategories.AcceptanceTest;
import com.mmakowski.util.TestClock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Category(AcceptanceTest.class)
public final class CmAnnAcceptanceTests {
    @Test
    public void postedMessagesCanBeRead() {
        try (Fixture test = new Fixture()) {
            test.assertOutput("> ");
            test.input("Alice -> I love the weather today");
            test.assertOutput("> ");
            test.advanceClock(Duration.ofMinutes(3));
            test.input("Bob -> Damn! We lost!");
            test.assertOutput("> ");
            test.advanceClock(Duration.ofMinutes(1));
            test.input("Bob -> Good game though.");
            test.assertOutput("> ");
            test.advanceClock(Duration.ofMinutes(1));
            test.input("Alice");
            test.assertOutput("I love the weather today (5 minutes ago)",
                              "> ");
            test.input("Bob");
            test.assertOutput("Good game though. (1 minute ago)",
                              "Damn! We lost! (2 minutes ago)",
                              "> ");
        }
    }

    @Test
    public void wallDisplaysPostsOfUsersToWhichWeAre() {
        try (Fixture test = new Fixture()) {
            test.assertOutput("> ");
            test.input("Alice -> I love the weather today");
            test.assertOutput("> ");
            test.advanceClock(Duration.ofMinutes(3));
            test.input("Bob -> Damn! We lost!");
            test.assertOutput("> ");
            test.advanceClock(Duration.ofMinutes(1));
            test.input("Bob -> Good game though.");
            test.assertOutput("> ");
            test.input("Charlie -> I'm in New York today! Anyone want to have a coffee?");
            test.assertOutput("> ");
            test.input("Charlie follows Alice");
            test.assertOutput("> ");
            test.advanceClock(Duration.ofSeconds(2));
            test.input("Charlie wall");
            test.assertOutput("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)",
                              "Alice - I love the weather today (5 minutes ago)",
                              "> ");
            test.input("Charlie follows Bob");
            test.assertOutput("> ");
            test.input("Charlie wall");
            test.assertOutput("Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)",
                              "Bob -> Good game though. (1 minute ago)",
                              "Bob -> Damn! We lost! (2 minutes ago)",
                              "Alice - I love the weather today (5 minutes ago)",
                              "> ");
        }
    }

    private static final class Fixture implements AutoCloseable {
        private final TestClock clock = new TestClock();
        private final BlockingBufferedInputOutput in = new BlockingBufferedInputOutput();
        private final BlockingBufferedInputOutput out = new BlockingBufferedInputOutput();
        private final ExecutorService asyncExecutor = Executors.newSingleThreadExecutor();
        private final TimeoutBasedAvailableLinesReader linesReader = TimeoutBasedAvailableLinesReader.withReadTimeout(Duration.ofMillis(100));

        public Fixture() {
            final CmAnnAssembly assembly = new CmAnnAssembly(in.reader, out.writer, clock);
            asyncExecutor.execute(assembly.repl);
        }

        public void input(final String line) {
            in.writer.writeLine(line);
        }

        public void assertOutput(final String... expectedLines) {
            final List<String> actualLines = linesReader.readAvailableLines(out.reader);
            Assert.assertEquals(ImmutableList.copyOf(expectedLines), actualLines);
        }

        public void advanceClock(final Duration duration) {
            clock.advance(duration);
        }

        @Override
        public void close() {
            ExecutorServices.shutdownSynchronously(asyncExecutor);
        }
    }
}

