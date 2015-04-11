package com.mmakowski.cmann.assembly;

import com.mmakowski.cmann.model.Command;
import com.mmakowski.cmann.model.Result;

public interface CommandExecutor {
    Result execute(Command command);
}
