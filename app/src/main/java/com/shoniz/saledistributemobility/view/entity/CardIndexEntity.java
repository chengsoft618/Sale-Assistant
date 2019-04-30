package com.shoniz.saledistributemobility.view.entity;


import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "CardIndex",primaryKeys = "PersonID")
public class CardIndexEntity extends BaseEntity {

    public int PersonID;
    public int AddressID;
    public String NeedDate;
    public long OrderNo;
    public int ChequeDuration;
    public String ErrorMessage;
    public String AccDesc;
    public String SaleDesc;
    public String RegDate;
}
