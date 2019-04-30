package com.shoniz.saledistributemobility.view.ordering.operation.verify;

import android.view.View;

import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerNavigator;

public interface IVerifyNavigator extends IRecyclerNavigator {
    void showPopupMenu(View v, long orderNo);

    void openOrderDetailActivity(long orderNo);
}
