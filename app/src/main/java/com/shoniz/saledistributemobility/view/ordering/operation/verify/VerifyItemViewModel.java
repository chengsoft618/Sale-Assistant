package com.shoniz.saledistributemobility.view.ordering.operation.verify;

import android.view.View;

import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemViewModel;

public class VerifyItemViewModel extends RecyclerItemViewModel<OrderCompleteData, //Long,
 IVerifyRecyclerListener> {

    @Override
    public boolean onClick(View v){
        getRecyclerListener().onOrderItemClick(v, getModel().getId());
        super.onClick(v);
        return false;
    }
}
