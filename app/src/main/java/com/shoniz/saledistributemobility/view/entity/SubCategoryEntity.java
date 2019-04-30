package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;


@Entity(tableName = "SubCategory",primaryKeys = "SubCategoryId")
public class SubCategoryEntity extends BaseEntity {

    public int CategoryId;
    public int SubCategoryId;
    public int ResourceFileId;
    public int Sort;
    public String SubCategoryName;
}
