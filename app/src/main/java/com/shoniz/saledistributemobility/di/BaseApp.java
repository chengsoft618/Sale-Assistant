package com.shoniz.saledistributemobility.di;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;

import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.framework.serialization.LogSerializer;
import com.shoniz.saledistributemobility.infrastructure.appmanagmant.AppWorker;
import com.shoniz.saledistributemobility.location.TrackingService;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


public class BaseApp extends DaggerApplication {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    AppComponent component;
    @Inject
    CommonPackage commonPackage;
    @Inject
    ILogRepository logRepository;
    private Thread.UncaughtExceptionHandler defaultHandler;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void startAppConfig(Context context, long intervalSync, boolean trackingMode) throws Exception {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (trackingMode)
                TrackingService.startService(context);
        }


        AppWorker.startAppWorker(intervalSync);


        //AppWorker. initWorkerIntents(context);

    }

    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });

        component = DaggerAppComponent.builder().getApp(this).build();
        component.inject(this);


        return component;
    }

    public void handleUncaughtException(Thread thread, Throwable e) {

        UncaughtException exception =
                new UncaughtException(
                        commonPackage, (Exception) e);
        exception.userMessage = "خطای مرگ آور";
        StackTraceElement[] arr = e.getStackTrace();
        StringBuilder report = new StringBuilder(e.toString());
        String lineSeparator = "-------------------------------\n\n";
        report.append("--------- Stack trace ---------\n\n");
        for (StackTraceElement anArr : arr) {
            report.append("    ");
            report.append(anArr.toString());
            report.append(lineSeparator);
        }

        exception.systemMessage = report.toString();
        try {
            new LogSerializer(getApplicationContext()).serialize(exception.getLogEntity());
        } catch (Exception e1) {
        }

        Utility.writeFile(report.toString());
        defaultHandler.uncaughtException(thread, e);
    }
}