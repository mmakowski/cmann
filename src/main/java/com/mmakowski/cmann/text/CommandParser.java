package com.mmakowski.cmann.text;

import com.mmakowski.cmann.model.Command;

public interface CommandParser {
    Command parse(String inputLine);
}
