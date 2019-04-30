package com.shoniz.saledistributemobility.view.ordering.operation.cancel;

import android.view.View;

import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerNavigator;

public interface IVerifyCancelNavigator extends IRecyclerNavigator {
    void showPopupMenu(View v, long orderNo);

    void openOrderDetailActivity(long orderNo);
}
