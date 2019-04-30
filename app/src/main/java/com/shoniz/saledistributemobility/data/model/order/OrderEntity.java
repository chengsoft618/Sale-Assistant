package com.shoniz.saledistributemobility.data.model.order;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.shoniz.saledistributemobility.data.BaseEntity;
import com.shoniz.saledistributemobility.data.database.DateConverter;

import java.util.UUID;

@Entity(tableName="Order", primaryKeys = {"OrderNo"})
public class OrderEntity extends BaseEntity {

    public Long OrderNo;
    public Long OrderSerial;
    public String RegDate;
    public int PersonID;
    public int TotalAmount;
    //public int BonusAmount;
    public boolean IsIssued;
    public String AccDesc;
    public String SalesDesc;
    public int ChequeDuration;
    public int InvoiceRemains;
    public int OrderTypeID;
    public int OrderWeight;
    public int OrderNetWeight;
    public int SenderId;
    public String Comment;
    public int ActionId;
    public int UserId;
    public long ActionDate;
    public boolean Verifiable;
    public String ClientOrderNo;
    public String ResponseDoc;
    public int IssuePrintedTime;

    @ColumnInfo(name = "Varity")
    @SerializedName("Varity")
    public int Variety;

    public long NeededCreditAmount;

    public String OrderStatus;


}
