package com.shoniz.saledistributemobility.view.path.customerlist;

import com.shoniz.saledistributemobility.view.base.viewmodel.INavigator;

public interface ICustomerListNavigator extends INavigator {
    void startCustomerActivity(int personId);
}
