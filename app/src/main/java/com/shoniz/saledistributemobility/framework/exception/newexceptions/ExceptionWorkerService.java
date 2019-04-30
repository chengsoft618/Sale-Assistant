package com.shoniz.saledistributemobility.framework.exception.newexceptions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.data.model.log.LogEntity;
import com.shoniz.saledistributemobility.data.model.log.LogRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.di.BaseApp;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.file.FileNotFoundError;
import com.shoniz.saledistributemobility.framework.file.LogFile;
import com.shoniz.saledistributemobility.framework.serialization.LogSerializer;
import com.shoniz.saledistributemobility.framework.serialization.SerializationError;
import com.shoniz.saledistributemobility.message.management.BroadcastMessage;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;


public class ExceptionWorkerService extends DaggerIntentService {


    @Inject
    ILogRepository logRepository;

    @Inject
    CommonPackage commonPackage;


    public ExceptionWorkerService() {
        super("ExceptionWorkerService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        RunnableMethod runDo = new RunnableMethod() {
            @Override
            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
                sendRemainedLogs(commonPackage);
                return null;
            }
        };
        SimpleAsyncTask asyncTask = new SimpleAsyncTask(null, runDo, null, null);
        asyncTask.runSerial();
    }

    public static boolean sendRemainedLogs(CommonPackage commonPackage) {
        File[] files = LogFile.getLogFilesPaths(commonPackage.getContext());
        List<LogEntity> logs = new ArrayList<>();
        for (File file : files) {
            Object logObject;
            try {
                logObject = new LogSerializer(commonPackage.getContext()).deserialize(file.toString());
                if (logObject instanceof LogEntity) {
                    file.delete();
                    logs.add((LogEntity) logObject);
                }
            } catch (Exception ex) {
            }
        }
        try {
            new LogRepository(commonPackage.getContext())
                    .sentLogsToServer(logs);
        } catch (Exception e) {
            try {
                new LogSerializer(commonPackage.getContext()).serialize(logs);
            } catch (FileNotFoundError fileNotFoundError) {
                fileNotFoundError.printStackTrace();
            } catch (InOutError inOutError) {
                inOutError.printStackTrace();
            } catch (SerializationError serializationError) {
                serializationError.printStackTrace();
            }
        }
        return files.length > 0;
    }
}
