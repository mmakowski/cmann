package com.mmakowski.cmann.assembly;

import com.mmakowski.cmann.model.Command;

public interface CommandParser {
    Command parse(String inputLine);
}
