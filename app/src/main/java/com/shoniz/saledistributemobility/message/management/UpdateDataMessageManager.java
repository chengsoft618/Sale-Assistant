package com.shoniz.saledistributemobility.message.management;

import android.content.Context;
import android.content.Intent;

import com.shoniz.saledistributemobility.data.model.location.LocationWorkerService;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;

/**
 * Created by 890683 on 7/14/2018.
 */

public class UpdateDataMessageManager implements IMessageManger {

    private MessageEntity messages;
    private Context context;


    public UpdateDataMessageManager(Context context, MessageEntity messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public void manageMessages() {
        Intent intent = new Intent(context, LocationWorkerService.class);
        context.startService(intent);
    }

}
