package com.mmakowski.cmann.acceptance;

import com.mmakowski.cmann.testcategories.AcceptanceTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;

@Category(AcceptanceTest.class)
public class CmAnnAcceptanceTests {
    @Test
    public void postedMessagesCanBeRead() {
        Fixture test = new Fixture();
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

    private static class Fixture {
        public void input(String line) {
            throw new UnsupportedOperationException("TODO");
        }

        public void assertOutput(String... lines) {
            throw new UnsupportedOperationException("TODO");
        }

        public void advanceClock(int time, TimeUnit timeUnit) {
            throw new UnsupportedOperationException("TODO");
        }
    }
}
