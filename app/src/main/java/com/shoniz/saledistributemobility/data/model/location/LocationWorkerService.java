package com.shoniz.saledistributemobility.data.model.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.location.LocationManagementService;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;


public class LocationWorkerService extends DaggerIntentService {

    @Inject
    ISettingRepository settingRepository;

    public LocationWorkerService() {
        super("LocationWorkerService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                if (settingRepository.isEnableLocationTracking()) {
                    startIntervalTracking(getApplicationContext(), settingRepository.getStartTrackingTime());
                    stopIntervalTracking(getApplicationContext(), settingRepository.getStopTrackingTime());
                } else {
                    stopIntervalTracking(getApplicationContext(), settingRepository.getStopTrackingTime());
                }
                checkIntervalTracking();
            } catch (Exception inOutError) {
                inOutError.printStackTrace();
            }

        }
    }

    private void checkIntervalTracking(){
        Calendar startCalender=getCalendar( settingRepository.getStartTrackingTime());
        Calendar stopCalender=getCalendar( settingRepository.getStopTrackingTime());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(System.currentTimeMillis());
        if(currentCalendar.getTimeInMillis()>startCalender.getTimeInMillis() &&
                currentCalendar.getTimeInMillis()<stopCalender.getTimeInMillis()){
            LocationManagementService.startService(getApplicationContext());
        }else {
            LocationManagementService.stopService(getApplicationContext());
        }
    }

    private void startIntervalTracking(Context context, String time) {
        Calendar calendar = getCalendar(time);

        Intent intent = new Intent(context, ExecTaskReceiver.class);
        intent.putExtra(ExecTaskReceiver.ExecTaskParam, ExecTaskReceiver.ExecTaskEnum.StartLocationTracking.ordinal());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, ExecTaskReceiver.ExecTaskEnum.StartLocationTracking.ordinal(), intent, 0);


        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmMgr != null) {
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
    }

    private void stopIntervalTracking(Context context, String time) {
        Calendar calendar = getCalendar(time);

        Intent intent = new Intent(context, ExecTaskReceiver.class);
        intent.putExtra(ExecTaskReceiver.ExecTaskParam, ExecTaskReceiver.ExecTaskEnum.StopLocationTracking.ordinal());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, ExecTaskReceiver.ExecTaskEnum.StopLocationTracking.ordinal(), intent, 0);


        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmMgr != null) {
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);
        }
    }


    @NonNull
    private Calendar getCalendar(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String tTime[] = time.split(":");
        int hour = Integer.parseInt(tTime[0]);
        int minute = Integer.parseInt(tTime[1]);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

}
