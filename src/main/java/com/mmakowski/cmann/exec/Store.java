package com.mmakowski.cmann.exec;

import com.mmakowski.cmann.model.Message;

import java.util.List;

public interface Store {
    void insertMessage(Message message);

    List<Message> messagesByUser(String userName);

    void insertSubsription(String follower, String followee);

    List<Message> wallMessages(String userName);
}
