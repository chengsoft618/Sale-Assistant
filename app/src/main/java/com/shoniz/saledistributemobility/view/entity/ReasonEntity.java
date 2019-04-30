package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "Reason", primaryKeys = "NotSallReasonID")
public class ReasonEntity extends BaseEntity {

    public int NotSallReasonID = 1;
    public String NotSallReasonText;
    public boolean IsActive = true;

}
