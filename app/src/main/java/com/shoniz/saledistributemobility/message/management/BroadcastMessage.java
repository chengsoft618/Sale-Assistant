package com.shoniz.saledistributemobility.message.management;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 921235 on 3/7/2018.
 */

public class BroadcastMessage {
    public final static String PERSONAL_BROADCAST_ACTION_NAME = "com.shoniz.saledistributemobility.PERSONAL_MESSAGE_BROADCAST_ACTION";
    public final static String SYSTEM_BROADCAST_ACTION_NAME = "com.shoniz.saledistributemobility.SYSTEM_MESSAGE_BROADCAST_ACTION";

    private static void sendPersonalMessageBroadcast(Context context, Bundle sendRequestModel) {
        Intent in = new Intent(BroadcastMessage.PERSONAL_BROADCAST_ACTION_NAME);
        in.putExtras(sendRequestModel);
        context.sendOrderedBroadcast(in, null);
    }

    private static void sendSystemMessageBroadcast(Context context, Bundle sendRequestModel) {
        Intent in = new Intent(BroadcastMessage.SYSTEM_BROADCAST_ACTION_NAME);
        in.putExtras(sendRequestModel);
        context.sendOrderedBroadcast(in, null);
    }

    public static void broadcast(Context context, Bundle bundle){
        sendPersonalMessageBroadcast(context, bundle);
        sendSystemMessageBroadcast(context, bundle);
    }

}
