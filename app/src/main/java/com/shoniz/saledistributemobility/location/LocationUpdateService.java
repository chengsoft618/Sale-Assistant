package com.shoniz.saledistributemobility.location;

import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


import com.shoniz.saledistributemobility.data.model.location.ILocationRepository;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;

import javax.inject.Inject;

import dagger.android.DaggerService;


public final class LocationUpdateService extends DaggerService {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//
//    private LocationHelper.LocationEventsListener locationEventsListener;
//    public LocationHelper locationHelper;
//
//    @Inject
//    ILocationRepository locationRepository;
//    @Inject
//    ISettingRepository settingRepository;
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        try {
//            locationEventsListener =
//                    new LocationHelper.LocationEventsListener() {
//
//
//                        @Override
//                        public void onGetLastKnownLocation(Location var1) {
//                            //if(var1 != null)
//                            //saveLocation(var1);
//                        }
//
//                        @Override
//                        public void onUpdateLocation(Location var1) {
//                            if (locationHelper != null)
//                                if (locationHelper.getFastestInterval() !=
//                                        settingRepository.getLocationUpdateIntervalSeconds() * 1000)
//                                    createLocationManager();
//                            //else
//                            //saveLocation(var1);
//                        }
//
//                        @Override
//                        public void onSettingChange(boolean var1) {
//
//                        }
//
//                        @Override
//                        public void onFailToTurnOnLocation(Exception exception) {
//
//                        }
//
//
//                    };
//            createLocationManager();
//
//
//        } catch (Exception e) {
//            int a = 0;
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    private void saveLocation(Location var1) {
//        LocationEntity locationEntity = new LocationEntity();
//        locationEntity.latitude = var1.getLatitude();
//        locationEntity.longitude = var1.getLongitude();
//        locationEntity.accuracy = var1.getAccuracy();
//
//        locationRepository.insert(locationEntity);
//    }
//
////    @android.support.annotation.Nullable
////    @Override
////    public IBinder onBind(Intent intent) {
////        return null;
////    }
//
//    private void createLocationManager() {
//        locationHelper = new LocationHelper(getApplicationContext(),
//                locationEventsListener,
//                settingRepository.getLocationUpdateIntervalSeconds(),
//                settingRepository.getLocationUpdateIntervalSeconds());
//        locationHelper.updateLastKnowLocation();
//        locationHelper.readyToUpdateLocation();
//        locationHelper.startLocationUpdates();
//    }
//
//
//    IBinder mBinder = new LocalBinder();
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }
//
//    public class LocalBinder extends Binder {
//        public LocationUpdateService getServerInstance() {
//            return LocationUpdateService.this;
//        }
//    }

}
