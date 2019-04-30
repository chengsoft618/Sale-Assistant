package com.shoniz.saledistributemobility.message.management;

import android.content.Context;

import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;

/**
 * Created by 921235 on 3/7/2018.
 */

public class IssuedMessageManager implements IMessageManger {

    private IMessageRepository messageRepository;
    private MessageEntity messages;


    public IssuedMessageManager(IMessageRepository messageRepository, MessageEntity messages) {
        this.messageRepository = messageRepository;
        this.messages = messages;
    }


    @Override
    public void manageMessages() {
        messageRepository.insert(messages);
    }
}
