package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "CardIndexDetail",primaryKeys = {"Shortcut","PersonId"})
public class CardIndexDetailEntity extends BaseEntity {

    @NonNull
    public String Shortcut;
    public int PersonId;
    public int RequestCarton;
    public int RequestPackage;
    public int ExistenceCarton;
    public int ExistencePackage;
}
