package com.shoniz.saledistributemobility.view.path.pathlist;

import com.shoniz.saledistributemobility.view.base.viewmodel.INavigator;

public interface IPathListNavigator extends INavigator {
    void goToCustomerPage(int personId);
    void syncCustomerInfoById(int personId);
}
