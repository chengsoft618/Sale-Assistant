package com.shoniz.saledistributemobility.data.model.customer;


import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

import java.util.concurrent.atomic.DoubleAccumulator;

@Entity(tableName = "CustomerBase",primaryKeys = "CustomerID")
public class CustomerBasicEntity extends BaseEntity {

    public int CustomerID;
    public int PersonID;
    public String PersonName;
    public String ContactName;
    public String Address;
    public String TelNo;
    public String CellNo;
    public String OwnerType;
    public String CustomerType;
    public int CustomerTypeID;
    public String ActiveSeason;
    public String MaxCredit;
    public String NotSaleReasonDate;
    public String NotSaleReasonDesc;
    public int PathCode;
    public String PathName;
    public boolean IsActive;
    public Long CreditRemain;
    public int AccountRemain;
    public String ClassNames;
    public double Latitude;
    public double Longitude;
    public Float accuracy;
    public float VisitOrder;
    public int LastVisitDays;
}
