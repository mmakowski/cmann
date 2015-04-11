package com.mmakowski.cmann.assembly;

import com.mmakowski.cmann.model.Command;
import com.mmakowski.cmann.model.Posting;
import com.mmakowski.cmann.model.Reading;

public final class TextSplittingCommandParser implements CommandParser {
    public Command parse(final String inputLine) {
        final String[] parts = inputLine.split(" -> ");
        if (parts.length == 2)
            return new Posting(parts[0], parts[1]);
        else
            return new Reading(parts[0]);
    }
}
