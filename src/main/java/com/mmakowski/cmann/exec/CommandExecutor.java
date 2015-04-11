package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.Command;
import com.mmakowski.cmann.model.Result;

public interface CommandExecutor {
    Result execute(Command command);
}
