package com.shoniz.saledistributemobility.data.model.user;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "role", primaryKeys = "roleId")
public class RoleEntity extends BaseEntity{

    public int roleId;

    public String roleName;

    public boolean canTerminate;

    public int directingPriority;
}
