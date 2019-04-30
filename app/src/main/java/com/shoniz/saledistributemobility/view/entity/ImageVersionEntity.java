package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.shoniz.saledistributemobility.data.BaseEntity;


@Entity(tableName = "ImageVersion", primaryKeys = "Shortcut")
public class ImageVersionEntity extends BaseEntity {

    @NonNull
    public String Shortcut;
    public int VersionNo;
}
