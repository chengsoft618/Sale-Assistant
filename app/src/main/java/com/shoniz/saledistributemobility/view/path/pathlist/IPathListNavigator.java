package com.shoniz.saledistributemobility.view.path.pathlist;

import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;
import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerNavigator;
import com.shoniz.saledistributemobility.view.path.PathModel__;

public interface IPathListNavigator extends IRecyclerNavigator {
    void goToCustomerList(PathEntity pathEntity);
    void goToCustomerPage(int personId, boolean couldFindCustomer);
    void refreshPathsRecycle();

}
