package com.mmakowski.cmann.text;

import com.mmakowski.cmann.model.Message;

public interface MessageFormat {
    String apply(Message message);
}
