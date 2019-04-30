package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;

/**
 * Created by ferdos.s on 6/1/2017.
 */

@Entity(tableName = "SubCategoryDetail",primaryKeys ="SubCategoryDetailId" )
public class SubCategoryDetailEntity{

    public int SubCategoryDetailId;
    public int SubCategoryId;
    public String Shortcut;
    public int Sort;
}
