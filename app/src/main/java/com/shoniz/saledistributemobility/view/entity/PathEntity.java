package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "Path", primaryKeys = "PathCode")
public class PathEntity extends BaseEntity {

    public int PathCode ;
    public String PathDescription ;
    public String PathEndPoint ;
    public String PathName ;
    public String PathStartPoint ;
    public int TourPeriod ;
    public int VisitPathRow ;
    public int ZoneCode;
    public int CustomerCount ;
    public int WholesalerCount ;
    public int RetailerCount ;
    public String PersianDate ;
    public boolean IsActive;

}
