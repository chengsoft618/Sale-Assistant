package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName="Category",primaryKeys = {"CategoryId"})
public class CategoryEntity extends BaseEntity {

    public int CategoryId;
    public int ProfileCategoryId;
    public int ResourceFileId;
    public int Sort;
    public String CategoryName;

}
