package com.shoniz.saledistributemobility.data.model.order;


import android.arch.persistence.room.Embedded;

import com.shoniz.saledistributemobility.data.MetaData;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;

public class OrderData extends MetaData<Long> {

    //public int RowNumber;

    @Embedded
    public OrderEntity order;

    @Embedded
    public PathEntity path;

    @Embedded(prefix = "cb_")
    public CustomerBasicEntity cutomer;

    @Override
    public Long getId() {
        return order.OrderNo;
    }
}
