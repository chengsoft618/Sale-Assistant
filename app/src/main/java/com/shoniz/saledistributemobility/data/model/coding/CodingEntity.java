package com.shoniz.saledistributemobility.data.model.coding;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "Coding", primaryKeys = "Shortcut")
public class CodingEntity extends BaseEntity {

    @NonNull
    public String Shortcut;
    public String ProductName;
    public int SalePrice;
    public int NetWeight;
    public String ProductDescription;
}
