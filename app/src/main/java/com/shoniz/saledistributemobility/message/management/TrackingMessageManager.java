package com.shoniz.saledistributemobility.message.management;

import android.content.Context;

import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.SettingPref;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.wialon.WialonWorker;
import com.shoniz.saledistributemobility.location.StopTrackingWorker;
import com.shoniz.saledistributemobility.location.TrackingService;
import com.shoniz.saledistributemobility.message.MessageTypeEnum;

/**
 * Created by 921235 on 3/7/2018.
 */

public class TrackingMessageManager implements IMessageManger {

    private MessageEntity messages;
    private Context context;


    public TrackingMessageManager(Context context, MessageEntity messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public void manageMessages() {
        TrackingService.stopService(context);
        if (messages.MessageTypeId == MessageTypeEnum.trackingOn.getValue()) {
            ISettingPref settingPref = new SettingPref(context);
            TrackingService.startService(context);
            try {
                StopTrackingWorker.setWorker(settingPref.getStopTrackingAfter());
            } catch (Exception e) {

            }

        }
    }


}
