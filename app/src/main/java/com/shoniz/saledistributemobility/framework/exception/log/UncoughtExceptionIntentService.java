package com.shoniz.saledistributemobility.framework.exception.log;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.google.gson.Gson;
import com.shoniz.saledistributemobility.data.model.log.LogEntity;
import com.shoniz.saledistributemobility.data.model.log.LogRepository;
import com.shoniz.saledistributemobility.framework.serialization.LogSerializer;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;

import dagger.android.DaggerIntentService;

public class UncoughtExceptionIntentService extends DaggerIntentService {
    LogEntity logEntity;

    private static final String LOG_JSON = "LogJson";

    public UncoughtExceptionIntentService() {
        super("UncoughtExceptionIntentService");
    }

    public static void start(Context context, LogEntity logEntity) {
        Intent intent = new Intent(context, UncoughtExceptionIntentService.class);
        intent.putExtra(LOG_JSON, new Gson().toJson(logEntity));
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String log = intent.getStringExtra(LOG_JSON);
            logEntity=new Gson().fromJson(log, LogEntity.class);
            handleLog();
        }
    }

    private void handleLog() {
        new SimpleAsyncTask(null, new RunnableMethod() {
            @Override
            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
                try {
                    new LogRepository(getApplicationContext()).sentLogToServer(logEntity);
                } catch (Exception e) {
                    try {
                        new LogSerializer(getApplicationContext()).serialize(logEntity);
                    } catch (Exception ex) { }
                }
                return null;
            }
        }, null, null).runSerial();
    }
}
