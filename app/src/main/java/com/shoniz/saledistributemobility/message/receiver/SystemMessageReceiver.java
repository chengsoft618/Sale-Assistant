package com.shoniz.saledistributemobility.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SystemMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        abortBroadcast();
        //new PersonalNotificationHandler(context).MessageHandler();
    }
}
