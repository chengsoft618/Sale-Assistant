package com.shoniz.saledistributemobility.data.model.customer;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "CustomerCheque")
public class CustomerChequeEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int ID;

    @NonNull public int PersonID;
    @NonNull public String SerialNumber;
    public int CustomerID;
    public String BankName;
    public String BankBranchName;
    public String PersonName;
    public String TotalPayment;
    public String FlowDate;
    public String ReasonName;
    public String TypeCheque;
    public String PaymentDate;
    public String DueDate;
    public int ConditionID;
}
