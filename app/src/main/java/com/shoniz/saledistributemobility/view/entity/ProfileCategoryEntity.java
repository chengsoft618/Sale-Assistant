package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "ProfileCategory", primaryKeys = "ProfileCategoryId")
public class ProfileCategoryEntity extends BaseEntity {

    public int ProfileCategoryId;
    public String ProfileName;

}
