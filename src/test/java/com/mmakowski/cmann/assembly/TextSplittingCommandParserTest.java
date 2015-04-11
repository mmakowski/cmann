package com.mmakowski.cmann.assembly;

import com.mmakowski.cmann.model.Command;
import com.mmakowski.cmann.model.Posting;
import com.mmakowski.cmann.model.Reading;
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
