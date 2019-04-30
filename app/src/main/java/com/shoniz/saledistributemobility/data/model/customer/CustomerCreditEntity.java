package com.shoniz.saledistributemobility.data.model.customer;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

/**
 * Created by ferdos.s on 7/3/2017.
 */

@Entity(tableName = "CustomerCredit",primaryKeys = "PersonID")
public class CustomerCreditEntity extends BaseEntity {

    public int CustomerID;
    public int PersonID;
    public int CustomerCreditFirstSixMonth;
    public int CustomerCreditSecondSixMonth;
}
