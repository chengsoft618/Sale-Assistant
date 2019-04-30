package com.shoniz.saledistributemobility.view.ordering.detail;

import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerNavigator;
import com.shoniz.saledistributemobility.view.ordering.detail.recycler.IOrderDetailRecyclerListener;

public interface IOrderDetailNavigator extends IRecyclerNavigator<IOrderDetailRecyclerListener> {

    void onPrintButtonClick(long v);

}
