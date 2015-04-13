package com.mmakowski.cmann.text;

import com.mmakowski.cmann.model.Command;
import com.mmakowski.cmann.model.Following;
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

    @Test
    public void parsesFollowing() {
        assertParsesTo(new Following("Charlie", "Alice"), "Charlie follows Alice");
    }

    private static void assertParsesTo(final Command expected, final String input) {
        Assert.assertEquals(expected, new TextSplittingCommandParser().parse(input));
    }
}
