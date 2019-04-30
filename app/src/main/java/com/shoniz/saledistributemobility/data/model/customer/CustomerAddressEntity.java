package com.shoniz.saledistributemobility.data.model.customer;


import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "CustomerAddress",primaryKeys = {"AddressID","PersonID"})
public class CustomerAddressEntity extends BaseEntity {

    public int AddressID;
    public int PersonID;
    public String Address;
}
