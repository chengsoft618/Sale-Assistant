package com.shoniz.saledistributemobility.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.location.ILocationRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.connectivity.ConnectionStateMonitor;
import com.shoniz.saledistributemobility.infrastructure.wialon.WialonService;
import com.shoniz.saledistributemobility.infrastructure.wialon.WialonWorker;
import com.shoniz.saledistributemobility.utility.dialog.YesNoDialog;

import javax.inject.Inject;

import dagger.android.DaggerService;

public class LocationManagementService extends DaggerService {

    LocationManager statusLocationManager;
    Activity currentActivity = null;
    LocationManager currentLocationManager;
    LocationListener currentLocationListener;
    LocationResultCallBack currentLocationResultCallBack;
    LocationManager trackingLocationManager;
    LocationListener trackingLocationListener;
    @Inject
    ILocationRepository locationRepository;
    @Inject
    ISettingRepository settingRepository;

    @Inject
    CommonPackage commonPackage;
    IBinder mBinder = new LocationManagementService.LocalBinder();
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private Context context;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        context = getApplicationContext();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        LocationCallback locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                locationRepository.insert(
                        LocationHelper.convertLocationsToLocationEntities(locationResult.getLocations())
                );
                try {
                    WialonWorker.setWorker();
                } catch (Exception e) {
                    UncaughtException uncaughtException1=new UncaughtException(commonPackage, e);
                    uncaughtException1.userMessage="UpdateErrorMessage";
                    ExceptionHandler.handle(uncaughtException1, commonPackage.getContext());
                }

            }
        };
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(settingRepository.getLocationUpdateIntervalSeconds());
        locationRequest.setFastestInterval(settingRepository.getLocationUpdateIntervalSeconds());
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setMaxWaitTime(settingRepository.getLocationMaxWaitTime());
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback, null);
        registerToManageLocationStatus();
        ConnectionStateMonitor.registerConnectivityNetworkMonitor(this);

        return super.onStartCommand(intent, flags, startId);
    }

    public void stopGettingCurrentLocation() {
        if (currentLocationListener != null) {
            currentLocationManager.removeUpdates(currentLocationListener);
            currentLocationListener = null;
        }
    }

    public void stopTracking() {
        if (trackingLocationListener != null) {
            trackingLocationManager.removeUpdates(trackingLocationListener);
            trackingLocationListener = null;
        }
    }


    @SuppressLint("MissingPermission")
    public void getCurrentLocation(LocationResultCallBack locationResultCallBack) {

        if (currentLocationManager == null)
            currentLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationResultCallBack == null) return;
        this.currentLocationResultCallBack = locationResultCallBack;
        currentLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (currentLocationResultCallBack != null)
                    currentLocationResultCallBack.getCurrentLocation(location);

                locationRepository.insert(
                        LocationHelper.convertLocationToLocationEntity(location)
                );

                try {
                    WialonWorker.setWorker();
                } catch (Exception e) {
                    UncaughtException uncaughtException1=new UncaughtException(commonPackage, e);
                    uncaughtException1.userMessage="UpdateErrorMessage";
                    ExceptionHandler.handle(uncaughtException1, commonPackage.getContext());
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        currentLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                settingRepository.getLocationUpdateIntervalSeconds(),
                1,
                currentLocationListener);
    }

    @SuppressLint("MissingPermission")
    public void startTracking() {
        if (trackingLocationManager == null)
            trackingLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        trackingLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                if (location != null) {
                    locationRepository.insert(
                            LocationHelper.convertLocationToLocationEntity(location)
                    );
                    try {
                        WialonWorker.setWorker();
                    } catch (Exception e) {
                        UncaughtException uncaughtException1=new UncaughtException(commonPackage, e);
                        uncaughtException1.userMessage="UpdateErrorMessage";
                        ExceptionHandler.handle(uncaughtException1, commonPackage.getContext());
                    }

                }
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        trackingLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                settingRepository.getTrackingUpdateIntervalSeconds(),
                1,
                trackingLocationListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @SuppressLint("MissingPermission")
    void registerToManageLocationStatus() {
        statusLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                int a = 0;

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                int a = 0;
            }

            public void onProviderEnabled(String provider) {
                int a = 0;
            }

            public void onProviderDisabled(String provider) {
                turnLocationOn(context);
            }
        };
        statusLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 60, 2000, locationListener);
    }

    public final void turnLocationOn(final Context context) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            LocationSettingsRequest.Builder builder =
                    new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            SettingsClient client = LocationServices.getSettingsClient(context);

            Task task = client.checkLocationSettings(builder.build());


            task.addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    if (exception instanceof ResolvableApiException && currentActivity != null)
                        try {
                            ((ResolvableApiException) exception).startResolutionForResult(
                                    currentActivity,
                                    LocationPermission.MULTIPLE_PERMISSIONS_KEY);
                        } catch (Exception e) {

                        }
                }
            });
        }



    }

    public void setCurrentActivity(Activity activity) {
        this.currentActivity = activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public LocationManagementService getServerInstance() {

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (!(locationManager!=null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) )
                turnLocationOn(getApplicationContext());

            return LocationManagementService.this;
        }
    }


    public static void startService(Context context, ServiceConnection serviceConnection) {
        Utility utility = new Utility(context);
        Intent mIntent = new Intent(context, LocationManagementService.class);
        if (!utility.isRunningService(LocationManagementService.class)) {
            context.startService(mIntent);
        }
        context.bindService(mIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    public static void startService(Context context) {
        stopService(context);
        Utility utility = new Utility(context);
        if (!utility.isRunningService(LocationManagementService.class)) {
            Intent mIntent = new Intent(context, LocationManagementService.class);
            context.startService(mIntent);
        }
    }

    public static void stopService(Context context) {

        Utility utility = new Utility(context);
        if (utility.isRunningService(LocationManagementService.class)) {
            Intent mIntent = new Intent(context, LocationManagementService.class);
            context.stopService(mIntent);
        }
    }


}
