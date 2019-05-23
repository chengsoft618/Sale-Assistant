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
}