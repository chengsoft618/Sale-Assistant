package com.shoniz.saledistributemobility.view.branch;

import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerNavigator;
import com.shoniz.saledistributemobility.view.base.viewmodel.INavigator;

public interface IBranchNavigator extends IRecyclerNavigator {
    void onEndAsync();
    void loadMainActivity();

}
