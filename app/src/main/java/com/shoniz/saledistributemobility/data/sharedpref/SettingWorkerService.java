package com.shoniz.saledistributemobility.data.sharedpref;

import android.content.Intent;

import com.shoniz.saledistributemobility.data.sharedpref.api.ISettingApi;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;

public class SettingWorkerService extends DaggerIntentService {

    @Inject
    ISettingRepository settingRepository;
    @Inject
    ISettingApi settingApi;

    public SettingWorkerService() {
        super("MessageWorkerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            settingRepository.sync();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
}
