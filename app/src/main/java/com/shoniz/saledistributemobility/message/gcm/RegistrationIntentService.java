package com.shoniz.saledistributemobility.message.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.SettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.SettingRepository;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.utility.dialog.RunnableModel;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;

import android.os.Bundle;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    public static void CreateInstance(Context context){
        Intent intent = new Intent(context, RegistrationIntentService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String token = "";
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
             token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            if (!token.isEmpty())
                Common.getPref(this).set(GcmPreferences.SENT_TOKEN_TO_SERVER, token);

        } catch (Exception e) {
            Exception ee = e;
        }

        Intent registrationComplete = new Intent(GcmPreferences.REGISTRATION_COMPLETE);
        Bundle b=new Bundle();
        b.putBoolean(GcmPreferences.SENT_TOKEN_TO_SERVER,!token.isEmpty());
        registrationComplete.putExtras(b);
        sendBroadcast(registrationComplete);
    }

    public static void sendRegistrationToServer(final Context context, final String token) {
        ISettingPref settingRepository=new SettingPref(context);
        RunnableMethod<Object, Object> runDo = new RunnableMethod<Object, Object>() {
            @Override
            public Object run(Object object, final OnProgressUpdate onProgressUpdate) {
                RunnableModel runnableModel = new RunnableModel();
                try {
                    if (settingRepository.isTokenCloudMessagingSave() ) {
                        new GcmApi().saveGcmToken(context, token);
                    }
                } catch (Exception e) {
                    runnableModel.exception = new HandleException(context, e);
                    runnableModel.HasError = true;
                }
                return runnableModel;
            }
        };

        RunnableMethod<RunnableModel, Object> runPost =
                new RunnableMethod<RunnableModel, Object>() {
                    @Override
                    public Object run(RunnableModel runnableModel, OnProgressUpdate onProgressUpdate) {
                        if (!runnableModel.HasError) {
                            settingRepository.setTokenCloudMessagingSave(true);
                        } else {
//                            ErrorDialog.showDialog( context,
//                                    runnableModel.exception.getUserTitle(),  runnableModel.exception.getUserMessage(),
//                                    runnableModel.exception.getSystemMessage());
                            runnableModel.exception.log();
                        }
                        return null;
                    }
                };

        SimpleAsyncTask asyncTask = new SimpleAsyncTask(null, runDo, runPost, null);
        asyncTask.runSerial();
    }

}