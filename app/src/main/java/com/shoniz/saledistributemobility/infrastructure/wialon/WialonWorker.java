package com.shoniz.saledistributemobility.infrastructure.wialon;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.location.StopTrackingWorker;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.view.customer.SendRequestModel;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.impl.WorkManagerImpl;

public class WialonWorker extends Worker {

    private static String tagWorker = "WialonWorker";

    public static void setWorker(SendRequestModel requestModel) throws Exception {


        try {
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            Data myData = new Data.Builder()
                    .putString(WialonService.SEND_REQUEST_MODEL_STRING, Utility.getJson(requestModel))
                    .build();

            WorkRequest workRequest = new OneTimeWorkRequest.
                    Builder(WialonWorker.class).
                    setInputData(myData).

                    setConstraints(constraints).build();

            WorkManager.getInstance().cancelAllWorkByTag(tagWorker);
            WorkManager.getInstance().enqueue(workRequest);
        }catch (Exception ex){
            throw new Exception(tagWorker+ex.getMessage());
        }


    }

    public static void setWorker() throws Exception {



        try {
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            WorkRequest workRequest = new OneTimeWorkRequest.Builder(WialonWorker.class).setConstraints(constraints).build();

            WorkManager.getInstance().cancelAllWorkByTag(tagWorker);
            WorkManager.getInstance().enqueue(workRequest);
        }catch (Exception ex){
            throw new Exception(tagWorker+ex.getMessage());
        }

    }

    @NonNull
    @Override
    public Result doWork() {

        Utility utility=new Utility(getApplicationContext());
        if(!utility.isRunningService(WialonService.class)){
            String str = getInputData().getString(WialonService.SEND_REQUEST_MODEL_STRING);
            if (str != null && !str.equals("")) {
                SendRequestModel sendRequestModel = Utility.getObject(str, new TypeToken<SendRequestModel>() {
                });
                WialonService.startService(getApplicationContext(), sendRequestModel);
            } else {
                WialonService.startService(getApplicationContext());
            }
        }
        return Result.SUCCESS;
    }
}
