package com.mmakowski.cmann.assembly;

import org.junit.Assert;
import org.junit.Test;

public final class TextSplittingCommandParserTest {
    @Test
    public void parsesPosting() {
        assertParsesTo(new Posting("Bob", "Damn! We lost!"), "Bob -> Damn! We lost!");
    }

    @Test
    public void parsesReading() {
        assertParsesTo(new Reading("Charlie"), "Charlie");
    }

    private static void assertParsesTo(final Command expected, final String input) {
        Assert.assertEquals(expected, new TextSplittingCommandParser().parse(input));
    }
}
