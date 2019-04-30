package com.shoniz.saledistributemobility.message.management;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;

/**
 * Created by 921235 on 3/7/2018.
 */

public class PersonalMessageManager implements IMessageManger {

    private IMessageRepository messageRepository;
    private MessageEntity messages;


    public PersonalMessageManager(IMessageRepository messageRepository, MessageEntity messages) {
        this.messageRepository = messageRepository;
        this.messages = messages;
    }

    @Override
    public void manageMessages() {
        messageRepository.insert(this.messages);
    }
}
