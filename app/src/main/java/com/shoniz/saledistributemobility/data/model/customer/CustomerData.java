package com.shoniz.saledistributemobility.data.model.customer;

import android.arch.persistence.room.Embedded;

import com.shoniz.saledistributemobility.data.MetaData;
import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;

public class CustomerData extends MetaData<Integer> {

    public CustomerData(CustomerBasicEntity customerBasicEntity) {
        this.customerBasicEntity = customerBasicEntity;
    }

    @Embedded
    public CustomerBasicEntity customerBasicEntity;

    public long UnIssuedOrderNo;
    public int NotSellReasonID;
    public long NeededCreditAmount;
    public String LastRegDate;
    public int daysCountAfter45DaysFromLastVisiting;

    @Override
    public Integer getId() {
        return customerBasicEntity.CustomerID;
    }
}
