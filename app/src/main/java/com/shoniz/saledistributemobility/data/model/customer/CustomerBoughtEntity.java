package com.shoniz.saledistributemobility.data.model.customer;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

/**
 * Created by ferdos.s on 7/31/2017.
 */

@Entity(tableName = "CustomerBuy",primaryKeys = {"CustomerID","YearTypeID"})
public class CustomerBoughtEntity extends BaseEntity {

    public int PersonID;
    public int YearTypeID;
    public long AmountBuyThisYear;
    public long QtyMainUnitBuyThisYear;
    public long QtySubUnitBuyThisYear;
    public long AmountBuyReturnedThisYear;
    public long QtyMainUnitBuyReturnedThisYear;
    public String YearType;
    public int CustomerID;
}
