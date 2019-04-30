package com.shoniz.saledistributemobility.data.model.customer.db;

import android.arch.persistence.room.Embedded;

import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.view.entity.CardIndexEntity;

public class CustomerBaseData {

    @Embedded
    public CustomerBasicEntity customerBasicEntity;

    public long UnIssuedOrderNo;
}
