package com.shoniz.saledistributemobility.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import java.util.Iterator;

public final class LocationManager {
    private boolean isAskedForTurnOnLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Context context;
    private LocationManager.LocationEventsListener locationListener;

    public final boolean isAskedForTurnOnLocation() {
        return this.isAskedForTurnOnLocation;
    }

    public final void setAskedForTurnOnLocation(boolean var1) {
        this.isAskedForTurnOnLocation = var1;
    }

    public LocationManager(Context context, LocationManager.LocationEventsListener locationListener, long interval, long fastestInterval) {
        super();
        this.context = context;
        this.locationListener = locationListener;
        this.locationRequest = this.createLocationRequest(interval, fastestInterval);
    }

    private final LocationRequest createLocationRequest(long interval, long fastestInterval) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(100);
        locationRequest.setInterval(interval);
        locationRequest.setFastestInterval(fastestInterval);
        return locationRequest;
    }


    @SuppressLint({"MissingPermission"})
    public final void getLastKnowPosition() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                locationListener.onGetLastKnownLocation(location);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public final void currentLocationSetting() {
        this.turnLocationOn(this.context);
    }


    public final void updateLocation() {
        this.locationCallback = (LocationCallback) (new LocationCallback() {
            public void onLocationAvailability(LocationAvailability p0) {
                LocationManager.LocationEventsListener var = LocationManager.this.locationListener;
                boolean isLocationAvailable = p0 != null && p0.isLocationAvailable();
                var.onSettingChange(isLocationAvailable);
                if (isLocationAvailable) {
                    LocationManager.this.setAskedForTurnOnLocation(false);
                }

                LocationManager.this.turnLocationOn(context);
            }

            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location;
                    LocationManager.LocationEventsListener listener;
                    for (Iterator var3 = locationResult.getLocations().iterator(); var3.hasNext(); listener.onUpdateLocation(location)) {
                        location = (Location) var3.next();
                        listener = LocationManager.this.locationListener;
                    }

                }
            }
        });
    }


    @SuppressLint({"MissingPermission"})
    public final void startLocationUpdates() {
        try {
            FusedLocationProviderClient providerClient = this.fusedLocationClient;
            providerClient.requestLocationUpdates(this.locationRequest, this.locationCallback, null);
        } catch (Exception var2) {

        }

    }


    public final void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }


    @SuppressLint({"MissingPermission"})
    public final void turnLocationOn(final Context context) {
        LocationSettingsRequest.Builder builder = (new LocationSettingsRequest.Builder()).addLocationRequest(this.locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(context);
        Task task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                if (LocationManager.this.locationListener != null) {
                    LocationManager.this.locationListener.onSettingChange(true);
                }
            }
        });

    }


    public interface LocationEventsListener {
        void onGetLastKnownLocation(Location var1);

        void onUpdateLocation(Location var1);

        void onSettingChange(boolean var1);
    }
}