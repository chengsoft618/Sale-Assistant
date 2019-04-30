package com.shoniz.saledistributemobility.infrastructure.appmanagmant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.shoniz.saledistributemobility.data.model.location.LocationWorkerService;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionWorkerService;
import com.shoniz.saledistributemobility.message.management.MessageManagementService;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;

public class AppWorker extends Worker {

    private static long interval = 30 * 1000 * 60;
    private static long minInterval = 15 * 1000 * 60;

    public static void startAppWorker(long intervalTime) throws Exception {

        String tagWorker = "AppWorker";
        try {
            interval = intervalTime;

            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
            WorkRequest workRequest;


            if (interval < minInterval) {
                interval = minInterval+1000;
            } else {


            }

            workRequest =
                    new PeriodicWorkRequest.Builder(AppWorker.class, interval, TimeUnit.MILLISECONDS)
                            .setConstraints(constraints)
                            .addTag(tagWorker)
                            .build();
            WorkManager.getInstance().cancelAllWorkByTag(tagWorker);
            WorkManager.getInstance().enqueue(workRequest);
        } catch (Exception ex) {
           throw new Exception(tagWorker + ex.getMessage());
        }
    }

    private static void initWorkerIntents(Context context) {
        Intent intent = new Intent(context, LocationWorkerService.class);
        context.startService(intent);

        intent = new Intent(context, MessageManagementService.class);
        context.startService(intent);

        intent = new Intent(context, ExceptionWorkerService.class);
        context.startService(intent);
    }

    @NonNull
    @Override
    public Result doWork() {

        try {
            Utility.writeFile("AppWorker Fire");
            initWorkerIntents(getApplicationContext());
        } catch (Exception ex) {
            ex.getMessage();
        }

        return Result.SUCCESS;
    }
}
