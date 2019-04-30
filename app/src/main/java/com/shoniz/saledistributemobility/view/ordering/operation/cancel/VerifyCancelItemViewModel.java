package com.shoniz.saledistributemobility.view.ordering.operation.cancel;

import android.view.View;

import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemViewModel;

public class VerifyCancelItemViewModel extends RecyclerItemViewModel<OrderCompleteData, //Long,
        IVerifyCancelRecyclerListener> {

    public boolean onClick(View v){
        getRecyclerListener().onOrderItemClick(v, getModel().getId());
        super.onClick(v);
        return false;
    }
}
