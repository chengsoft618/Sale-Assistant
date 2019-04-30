package com.shoniz.saledistributemobility.data.model.order.verifying;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "userAction" ,primaryKeys = "actionId")
public class ActionEntity extends BaseEntity {

    public int actionId;

    public String actionName;
}
