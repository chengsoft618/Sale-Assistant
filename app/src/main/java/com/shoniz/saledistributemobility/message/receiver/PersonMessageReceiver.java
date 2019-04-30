package com.shoniz.saledistributemobility.message.receiver;

import android.content.Context;
import android.content.Intent;

import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.message.notification.PersonalNotificationHandler;

import javax.inject.Inject;

import dagger.android.DaggerBroadcastReceiver;

public class PersonMessageReceiver extends DaggerBroadcastReceiver {

    @Inject
    IMessageRepository messageRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);
        abortBroadcast();
        new PersonalNotificationHandler(context,messageRepository.unreadMessage()).MessageHandler();
    }
}
