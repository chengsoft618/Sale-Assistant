package com.shoniz.saledistributemobility.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;

import java.util.ArrayList;
import java.util.List;

public class LocationHelper {

    public static LocationEntity convertLocationToLocationEntity(Location location) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.accuracy = location.getAccuracy();
        locationEntity.latitude = location.getLatitude();
        locationEntity.longitude = location.getLongitude();
        locationEntity.locationDate = location.getTime();
        locationEntity.bearing = location.getBearing();
        locationEntity.speed = location.getSpeed();
        locationEntity.satellites = Getsatnum(location);
        locationEntity.altitude = location.getAltitude();
        return locationEntity;
    }

    public static List<LocationEntity> convertLocationsToLocationEntities(List<Location> locations) {
        List<LocationEntity> locationEntities = new ArrayList<>();
        for (Location location : locations)
            locationEntities.add(convertLocationToLocationEntity(location));
        return locationEntities;
    }

    public static int Getsatnum(Location location) {
        if (location.getExtras() != null) {
            if (location.getExtras().containsKey("satellites")) {
                int nSatellites = location.getExtras().getInt("satellites", 0);
                return nSatellites;
            } else {
                return 0;
            }

        } else {
            return 0;
        }
    }

//    //private boolean isAskedForTurnOnLocation = false;
//    private FusedLocationProviderClient fusedLocationClient;
//    private LocationRequest locationRequest;
//    private LocationCallback locationCallback;
//    private Context context;
//    private LocationHelper.LocationEventsListener locationListener;
//    LocationHelper.LocationEventsListener activityEventListener;
//    private long interval;
//    private long fastestInterval;
//    LocationManager locationManager;
//
//    public void setActivityEventListener(LocationEventsListener activityEventListener) {
//        this.activityEventListener = activityEventListener;
//    }
//
////    public final boolean isAskedForTurnOnLocation() {
////        return this.isAskedForTurnOnLocation;
////    }
//
////    public final void setAskedForTurnOnLocation(boolean var1) {
////        this.isAskedForTurnOnLocation = var1;
////    }
//
//    @SuppressLint("MissingPermission")
//    public LocationHelper(Context context, LocationHelper.LocationEventsListener LocationEventsListener, long interval, long fastestInterval) {
//        this.context = context;
//        this.locationListener = LocationEventsListener;
//        this.locationRequest = createLocationRequest(interval, fastestInterval);
//        this.interval = interval;
//        this.fastestInterval = fastestInterval;
//
//
//        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        LocationListener locationListener = new LocationListener() {
//            public void onLocationChanged(Location location) {
//                int a = 0;
//
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                int a = 0;
//            }
//
//            public void onProviderEnabled(String provider) {
//                int a = 0;
//            }
//
//            public void onProviderDisabled(String provider) {
//                turnLocationOn(context);
//            }
//        };
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0.2f, locationListener);
//
//    }
//
//    private final LocationRequest createLocationRequest(long interval, long fastestInterval) {
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(interval);
//        locationRequest.setFastestInterval(fastestInterval);
//        return locationRequest;
//    }
//
//    @SuppressLint({"MissingPermission"})
//    public final void updateLastKnowLocation() {
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
//            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (locationListener != null)
//                        locationListener.onGetLastKnownLocation(location);
//                    if (activityEventListener != null)
//                        activityEventListener.onGetLastKnownLocation(location);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {}
//            });
//    }
//
//
//    public final void readyToUpdateLocation() {
//        this.locationCallback = new LocationCallback() {
//             public void onLocationAvailability(LocationAvailability p0) {
//
//                boolean isLocationAvailable = p0 != null && p0.isLocationAvailable();
//                if (locationListener != null)
//                    locationListener.onSettingChange(isLocationAvailable);
//                if (activityEventListener != null)
//                    activityEventListener.onSettingChange(isLocationAvailable);
//                if (isLocationAvailable) {
//                    //isAskedForTurnOnLocation = false;
//                }
//                turnLocationOn(context);
//
//            }
//
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) turnLocationOn(context);
//                for (Location location : locationResult.getLocations()) {
//                    if (locationListener != null)
//                        locationListener.onUpdateLocation(location);
//                    if (activityEventListener != null)
//                        activityEventListener.onUpdateLocation(location);
//
//                }
//            }
//        };
//    }
//
//    @SuppressLint({"MissingPermission"})
//    public final void startLocationUpdates() {
//        try {
//            fusedLocationClient.requestLocationUpdates(
//                    locationRequest, locationCallback, null);
//        } catch (Exception var2) {
//
//        }
//    }
//
//    public final void stopLocationUpdates() {
//        fusedLocationClient.removeLocationUpdates(locationCallback);
//    }
//
//
//    public final void turnLocationOn(final Context context) {
//
//        LocationSettingsRequest.Builder builder =
//                new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//
//        SettingsClient client = LocationServices.getSettingsClient(context);
//
//        Task task = client.checkLocationSettings(builder.build());
//
//
//        task.addOnSuccessListener(new OnSuccessListener() {
//            @Override
//            public void onSuccess(Object o) {
//                if (locationListener != null) {
//                    locationListener.onSettingChange(true);
//                }
//                if (activityEventListener != null) {
//                    activityEventListener.onSettingChange(true);
//                }
//            }
//        });
//        task.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                if (exception instanceof ResolvableApiException) {
//                    if (locationListener != null)
//                        locationListener.onFailToTurnOnLocation(exception);
//                    if (activityEventListener != null)
//                        activityEventListener.onFailToTurnOnLocation(exception);
//                }
//            }
//        });
//
//
//    }
//
//
//    public interface LocationEventsListener {
//        void onGetLastKnownLocation(Location var1);
//
//        void onUpdateLocation(Location var1);
//
//        void onSettingChange(boolean var1);
//
//        void onFailToTurnOnLocation(Exception exception);
//    }
//
//    public long getInterval() {
//        return interval;
//    }
//
//    public long getFastestInterval() {
//        return fastestInterval;
//    }
}