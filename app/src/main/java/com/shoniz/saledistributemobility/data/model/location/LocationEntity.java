package com.shoniz.saledistributemobility.data.model.location;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;


@Entity(tableName = "Location",primaryKeys = "locationDate")
public class LocationEntity extends BaseEntity {

    public long getLocationDate() {
        return locationDate;
    }

    public void setLocationDate(long locationDate) {
        this.locationDate = locationDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getlongitude() {
        return longitude;
    }

    public void setlongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getSatellites() {
        return satellites;
    }

    public void setSatellites(int satellites) {
        this.satellites = satellites;
    }

    public long locationDate ;
    public double latitude;
    public double longitude;
    public double altitude;
    public float accuracy;
    public float bearing;
    public float speed;
    public int satellites;
}
