package com.shoniz.saledistributemobility.view.path.customerlist;

import com.shoniz.saledistributemobility.data.model.customer.CustomerData;
import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerNavigator;

public interface ICustomerListNavigator extends IRecyclerNavigator<Integer> {
    void startCustomerActivity(int personId);
    void refreshRecycle();
    void showSentOrderOperationDialog(CustomerData customerData);
    void startActivity(int personId);
    void showUnvisitedCustomerDialog(int personId);
}
