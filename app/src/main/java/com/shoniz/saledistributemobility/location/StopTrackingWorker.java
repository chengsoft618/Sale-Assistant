package com.shoniz.saledistributemobility.location;

import android.support.annotation.NonNull;

import com.shoniz.saledistributemobility.framework.Utility;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;

public class StopTrackingWorker extends Worker {

    public static void setWorker(long stopTrackingAfter) throws Exception {
        String tagWorker = "StopTrackingWorker";
        WorkRequest workRequest;

        try {
            workRequest = new OneTimeWorkRequest.Builder(StopTrackingWorker.class).setInitialDelay(stopTrackingAfter, TimeUnit.MILLISECONDS).build();
            WorkManager.getInstance().cancelAllWorkByTag(tagWorker);
            WorkManager.getInstance().enqueue(workRequest);
        }catch (Exception ex){
            throw new Exception(tagWorker+ex.getMessage());
        }


    }

    @NonNull
    @Override
    public Result doWork() {
        Utility.writeFile("StopTrackingWorker Fire");
        TrackingService.stopService(getApplicationContext());
        return Result.SUCCESS;
    }
}
