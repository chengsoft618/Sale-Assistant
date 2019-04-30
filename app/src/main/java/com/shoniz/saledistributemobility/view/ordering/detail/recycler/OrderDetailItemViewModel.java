package com.shoniz.saledistributemobility.view.ordering.detail.recycler;

import android.view.View;

import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemViewModel;

public class OrderDetailItemViewModel extends RecyclerItemViewModel<OrderDetailCompleteData, //Long,
        IOrderDetailRecyclerListener> {

    public boolean onClick(View v){
        getRecyclerListener().onOrderItemClick(v, getModel().getId());
        super.onClick(v);
        return false;
    }
}
