package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "FileResource", primaryKeys = "ResourceFileId")
public class FileResourceEntity extends BaseEntity {

    public int ResourceFileId;
    public int VersionNo;
}
