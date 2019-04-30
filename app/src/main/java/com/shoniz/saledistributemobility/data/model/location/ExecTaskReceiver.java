package com.shoniz.saledistributemobility.data.model.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shoniz.saledistributemobility.location.LocationManagementService;
import com.shoniz.saledistributemobility.utility.Notification;

public class ExecTaskReceiver extends BroadcastReceiver {

    public static String ExecTaskParam = "ExecTaskParam";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ExecTaskEnum execTaskEnum = ExecTaskEnum.values()[bundle.getInt(ExecTaskParam)];
            switch (execTaskEnum) {
                case StartLocationTracking:
                    LocationManagementService.startService(context);
                    break;

                case StopLocationTracking:
                    LocationManagementService.stopService(context);
                    break;

                default:
                    LocationManagementService.stopService(context);

            }

        }
    }

    public enum ExecTaskEnum {
        StartLocationTracking,
        StopLocationTracking

    }

}
