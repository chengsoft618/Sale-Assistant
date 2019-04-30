package com.shoniz.saledistributemobility.view.ordering.detail.recycler;

import android.view.View;

import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerListener;

public interface IOrderDetailRecyclerListener extends IRecyclerListener {
    void onOrderItemClick(View view, long orderNo);
}
