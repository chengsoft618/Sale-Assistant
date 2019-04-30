package com.shoniz.saledistributemobility.view.ordering.operation.cancel;

import android.view.View;

import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerListener;

public interface IVerifyCancelRecyclerListener extends IRecyclerListener {
    void onOrderItemClick(View view, long orderNo);
}
