package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;


@Entity(tableName = "Location",primaryKeys = "locationDate")
public class LocationEntity extends BaseEntity {

    public Long locationDate ;
    public Double latitude;
    public Double longitude;
    public Float accuracy;
}
