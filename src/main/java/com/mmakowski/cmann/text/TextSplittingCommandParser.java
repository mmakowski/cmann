package com.mmakowski.cmann.text;

import com.mmakowski.cmann.model.*;

public final class TextSplittingCommandParser implements CommandParser {
    public Command parse(final String inputLine) {
        final String[] parts = inputLine.split(" ", 3);
        if (parts.length > 1) {
            final String userName = parts[0];
            final String operation = parts[1];
            final String argument = parts.length == 3 ? parts[2] : "";
            return makeCommand(operation, userName, argument);
        } else
            return new Reading(parts[0]);
    }

    private Command makeCommand(final String operation, final String userName, final String argument) {
        switch (operation) {
            case "->":
                return new Posting(userName, argument);
            case "follows":
                return new Following(userName, argument);
            case "wall":
                return new Wall(userName);
            default:
                throw new RuntimeException("unsupported operation: " + operation);
        }
    }
}
