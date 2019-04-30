package com.shoniz.saledistributemobility.view.ordering.operation.verify;

import android.view.View;

import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerListener;

public interface IVerifyRecyclerListener extends IRecyclerListener {
    void onOrderItemClick(View view, long orderId);
}
