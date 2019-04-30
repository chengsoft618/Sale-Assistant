package com.shoniz.saledistributemobility.data.model.order;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "OrderDetail", primaryKeys = {"OrderNo", "Row"})
public class OrderDetailEntity extends BaseEntity {

    public long OrderNo;
    public boolean IsBonus;
    public int Row;
    public int Price;
    public int Qty;
    public int UnitId;
    public String Shortcut;
}

