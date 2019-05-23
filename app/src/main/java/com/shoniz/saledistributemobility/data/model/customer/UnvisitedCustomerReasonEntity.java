package com.shoniz.saledistributemobility.data.model.customer;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "UnvisitedCustomerReason", primaryKeys = "PersonId")
public class UnvisitedCustomerReasonEntity extends BaseEntity {

    public int PersonId;
    public int NotSallReasonID;
    public String ErrorMessage;
    public boolean IsSent;
    public String Description;

}
