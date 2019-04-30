package com.shoniz.saledistributemobility.data.model.user;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "user", primaryKeys = {"userId", "roleId"})
public class UserEntity extends BaseEntity {

    public int userId;

    public String fullName;

    public int roleId;

    public int officerUserId;

}
