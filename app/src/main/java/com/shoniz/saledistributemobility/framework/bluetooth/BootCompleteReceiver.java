package com.shoniz.saledistributemobility.framework.bluetooth;

import android.content.Context;
import android.content.Intent;

import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.SettingRepository;
import com.shoniz.saledistributemobility.di.BaseApp;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;

import javax.inject.Inject;

import dagger.android.DaggerBroadcastReceiver;

public class BootCompleteReceiver extends DaggerBroadcastReceiver {

    @Inject
    ISettingRepository settingRepository;

    @Inject
    CommonPackage commonPackage;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        try {
            BaseApp.startAppConfig(context,
                    settingRepository.getSyncIntervalSeconds(),
                    settingRepository.getIsInTrackingMode());
        } catch (Exception e1) {
            UncaughtException uncaughtException1=new UncaughtException(commonPackage, e1);
            uncaughtException1.userMessage="BootCompleteReceiver";
            ExceptionHandler.handle(uncaughtException1, commonPackage.getContext());
        }


    }
}
