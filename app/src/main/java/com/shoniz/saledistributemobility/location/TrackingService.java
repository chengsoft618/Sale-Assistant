package com.shoniz.saledistributemobility.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.shoniz.saledistributemobility.framework.Utility;

import dagger.android.DaggerService;

public class TrackingService extends DaggerService {


    boolean mLocationBounded;
    LocationManagementService mLocationServer;

    ServiceConnection mLocationConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLocationBounded = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLocationBounded = true;
            LocationManagementService.LocalBinder mLocalBinder = (LocationManagementService.LocalBinder) service;
            mLocationServer = mLocalBinder.getServerInstance();
            mLocationServer.startTracking();
        }
    };





    public static void startService(Context context) {
        Utility utility = new Utility(context);
        if (!utility.isRunningService(TrackingService.class)) {
            Intent mIntent = new Intent(context, TrackingService.class);
            context.startService(mIntent);
        }
    }

    public static void stopService(Context context) {
        Utility utility = new Utility(context);
        if (utility.isRunningService(TrackingService.class)) {
            Intent mIntent = new Intent(context, TrackingService.class);
            context.stopService(mIntent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LocationManagementService.startService(this,mLocationConnection);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mLocationServer.stopTracking();
        if (mLocationBounded) {
            try {
                unbindService(mLocationConnection);
                mLocationBounded = false;
            } catch (Exception e) {
               e.getMessage();
            }

        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
