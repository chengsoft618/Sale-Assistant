package com.shoniz.saledistributemobility.infrastructure.recycler;

import com.shoniz.saledistributemobility.view.base.viewmodel.INavigator;

public interface IRecyclerActivityNavigator{
    void onLongClick();
    void onSingleItemSelected();
    void onModelChange();
    void onSelectAll();
    void onUnSelectAll();
}
