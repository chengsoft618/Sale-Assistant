package com.shoniz.saledistributemobility.data.model.order.ordercomplete;


import android.arch.persistence.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;
import com.shoniz.saledistributemobility.data.MetaData;

public class OrderCompleteData extends MetaData<Long> {

    public Long OrderNo;
    public Long OrderSerial;
    public String RegDate;
    public int PersonID;
    public int CustomerID;
    public int TotalAmount;
    public int BonusAmount;
    public String CellNo;
    public boolean IsIssued;
    public boolean InPath;
    public String AccDesc;
    public String SalesDesc;
    public String Address;
    public String TelNo;
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
    @ColumnInfo(name = "Varity")
    @SerializedName("Varity")
    public int Variety;
    public String CustomerName;
    public String FullName;
    public String SenderName;
    public int PathCode;
    public String PathName;
    public int IssuePrintedTime;
    public String ClientOrderNo;
    public String ResponseDoc;
    public String OrderStatus;


    @Override
    public Long getId() {
        return OrderNo;
    }
}
